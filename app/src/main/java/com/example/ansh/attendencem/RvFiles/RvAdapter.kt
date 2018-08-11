package com.example.ansh.attendencem.RvFiles

import android.arch.lifecycle.LifecycleOwner
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.example.ansh.attendencem.*
import kotlinx.android.synthetic.main.eachrow_attendence.view.*
import java.util.*


class RvAdapter(viewModel: LiveSubjectsManager, owner: LifecycleOwner) : RecyclerView.Adapter<RvHolder>() {
    private var dataList: List<SubjectModel> = LinkedList()//this is just a zombie copy do not use it for updation or deletin because changes in it won't get reflected in database . instead, we will be using it only to set the layout initially or when data changes(the notifyOndatasetchange call.). to modify using btn attend / miss,first get subject that was touched from the  datalist. search it in the database and modify(i.e, insert)
    private  var liveManager:LiveSubjectsManager = viewModel

    init {
        enableLiveManagerUpdates(owner)
    }

    private fun enableLiveManagerUpdates(owner: LifecycleOwner) {
        this.liveManager.getAllSubjects().observe(owner,android.arch.lifecycle.Observer { livelist ->
            if(livelist!=null) {
                this.dataList = livelist
            }
            else{
                this.dataList = LinkedList()
            }
            notifyDataSetChanged()

        })
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
        holder.itemView.textTotalAttendence.text = StringBuilder()
                .append(data.attended)
                .append('/')
                .append(data.total).toString()
        holder.itemView.textPercentage.text = "$percentage"//setting percentage

        setEachViewsColor(holder, percentage)
        holder.itemView.textSubject.isSelected = true // for marquee


        holder.itemView.btAttend.setOnClickListener {
            UiUtils.SubjAdapterUtils.vibrate(it.context, 100)
            ModifyDatabase(dataList.get(pos), UserAction.BT_ATTENDED)

        }

        holder.itemView.btMiss.setOnClickListener {
            UiUtils.SubjAdapterUtils.vibrate(it.context, 100)
            ModifyDatabase(dataList.get(pos), UserAction.BT_MISSED)
        }
        holder.itemView.setOnClickListener {
            Toast.makeText(it.context, "Tip. LongPress to modify or delete the subject", Toast.LENGTH_LONG).show()
        }

        holder.itemView.setOnLongClickListener {
            UiUtils.showSubjectEditorDialog(holder.itemView.context,liveManager, dataList[pos])
            true
        }


    }

    private fun setEachViewsColor(holder: RvHolder, percentage: Double) {
        //idea 0 : fix color for each row[UGLY]
        //idea 1: color of each row is to be based on the internal id, therefore making color as random
        //current idea : each row's color is determined by attence percentage
//        val arr: IntArray = intArrayOf(
//                R.color.g_blue, R.color.g_red,
//                R.color.g_yellow, R.color.g_green
//        )

        val percentRes: Int

        if (percentage < 60) percentRes = R.color.g_red
        else if (percentage < 75) percentRes = R.color.g_yellow
        else if (percentage < 85) percentRes = R.color.g_green
        else percentRes = R.color.g_blue

        val percentcolor =UiUtils.getColorFromResources(holder.itemView.context,percentRes)


        //subject color
        holder.itemView.textSubject.setBackgroundColor(percentcolor)
        //btns color
        holder.itemView.btAttend.setColorFilter(percentcolor, android.graphics.PorterDuff.Mode.SRC_IN)
        holder.itemView.btMiss.setColorFilter(percentcolor, android.graphics.PorterDuff.Mode.SRC_IN)
        //sidelinecolor
        holder.itemView.coloredLine1.setBackgroundColor(percentcolor)
        holder.itemView.coloredLine2.setBackgroundColor(percentcolor)

        //current percentage color
        holder.itemView.textPercentage.setTextColor(percentcolor)

    }

    enum class UserAction { BT_MISSED, BT_ATTENDED }
    fun ModifyDatabase(subjectModel: SubjectModel, userAction: UserAction) {

        if (userAction == UserAction.BT_ATTENDED) {
            subjectModel.attended++
            subjectModel.total++
        } else if (userAction == UserAction.BT_MISSED) {
            subjectModel.total++
        }

        liveManager.insertSubjects(subjectModel)

    }


    override fun getItemCount(): Int {
        return  dataList.size

    }


}

