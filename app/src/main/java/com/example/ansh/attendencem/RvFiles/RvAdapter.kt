package com.example.ansh.attendencem.RvFiles

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.example.ansh.attendencem.R
import com.example.ansh.attendencem.SubjectModel
import com.example.ansh.attendencem.UiUtils
import kotlinx.android.synthetic.main.eachrow_attendence.view.*
import java.util.*


class RvAdapter : RecyclerView.Adapter<RvHolder> {
    private var dataList: LinkedList<SubjectModel> // if declared private jvm won't generate getters/setters


    constructor(dataList: LinkedList<SubjectModel>) : super() {
        this.dataList = dataList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvHolder {
        val view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.eachrow_attendence, parent, false)

        return RvHolder(view)

    }


    override fun onBindViewHolder(holder: RvHolder, pos: Int) {
        val data = dataList[pos]
        val percentage =UiUtils.SubjAdapterUtils.getPercentage(data.attended,data.total)//calculating percentage

        holder.itemView.textSubject.text = data.subjectName //setting subject name
        holder.itemView.textTotalAttendence.text = "${data.attended.toString()}/${data.total}"//setting subject total attendence
        holder.itemView.textPercentage.text = "${percentage}"//setting percentage

        setEachViewsColor(holder, percentage, pos)
        holder.itemView.textSubject.isSelected = true // for marquee


        holder.itemView.btAttend.setOnClickListener {
            UiUtils.SubjAdapterUtils.vibrate(it.context, 100)
            ModifyDatabase(pos, UserAction.BT_ATTEND)

        }

        holder.itemView.btMiss.setOnClickListener {
            UiUtils.SubjAdapterUtils.vibrate(it.context, 100)
            ModifyDatabase(pos, UserAction.BT_MISS)
        }
        holder.itemView.setOnClickListener {

            Toast.makeText(it.context, "Tip. LongPress to modify or delete the subject", Toast.LENGTH_LONG).show()
        }

        holder.itemView.setOnLongClickListener() {
            UiUtils.showSubjectEditorDialog(holder.itemView.context, data)
            true
        }

    }

    fun setEachViewsColor(holder: RvHolder, percentage: Double, pos: Int) {
        val arr: IntArray = intArrayOf(
                R.color.g_blue, R.color.g_red,
                R.color.g_yellow, R.color.g_green
        )

        //subject color
        holder.itemView.textSubject.setBackgroundColor(holder.itemView.resources.getColor(arr[pos % arr.size]))

        //btns color
        holder.itemView.btAttend.setColorFilter(
                ContextCompat.getColor(holder.itemView.context, arr[pos % arr.size]),
                android.graphics.PorterDuff.Mode.SRC_IN)
        holder.itemView.btMiss.setColorFilter(
                ContextCompat.getColor(holder.itemView.context, arr[pos % arr.size]),
                android.graphics.PorterDuff.Mode.SRC_IN)

        val percentcolor: Int

        if (percentage < 60) percentcolor = R.color.g_red
        else if (percentage < 75) percentcolor = R.color.g_yellow
        else if (percentage < 85) percentcolor = R.color.g_green
        else percentcolor = R.color.g_blue

        //current percentage color
        holder.itemView.textPercentage.setTextColor(holder.itemView.resources.getColor(percentcolor))

    }

    enum class UserAction { BT_MISS, BT_ATTEND }
    fun ModifyDatabase(pos: Int, userAction: UserAction) {
        //todo our adapter will not require any external data set. it wil simply have exposed function modify database
        //todo for attend / miss add subject /remove and modify subject logic of attend /miss is provide inside that fuction only
        //todo logic for add modify and delete subject will be written outside
        //todo and one can simply call this function with UPDATE_ExterNAL. from wherever the call is , it would be after making changes TOTHE DATABASE . this function will simply accept them(or help in doing them if not done already , get these changes in the ui( via getadapter set adapter and database call) and call notify data set change
        // very bad approach since we are not using livedata:\

        //for now just change the provided data set

        if (userAction == UserAction.BT_ATTEND) {
            dataList[pos].attended++
            dataList[pos].total++
        } else if (userAction == UserAction.BT_MISS) {
            dataList[pos].total++
        }

        notifyDataSetChanged()

    }

    fun getDataList(): LinkedList<SubjectModel>? {
        return dataList
    }
    fun setDataList(dataList: LinkedList<SubjectModel>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (getDataList() == null) 0 else getDataList()!!.size
    }


}

