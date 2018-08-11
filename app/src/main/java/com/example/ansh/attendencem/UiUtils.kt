package com.example.ansh.attendencem

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.example.ansh.attendencem.DBFiles.SubjectsManager
import kotlinx.android.synthetic.main.al_new_subject.view.*
import java.text.SimpleDateFormat
import java.util.*

object UiUtils {
    private val TAG = "UiUTILS"

    fun showSubjectEditorDialog(context: Context, data: SubjectModel? = SubjectModel()) {//data won't be ever null becaue we covered it with empty default object and context is always mandatory
        Log.e(TAG, "showSubjectEditorDialog :called with args:$context,$data")


        @SuppressLint("InflateParams")// for null root, not any problem tho: https://stackoverflow.com/questions/24832497
        val v = LayoutInflater
                .from(context)
                .inflate(R.layout.al_new_subject, null, false)

        setViewsInternalActions(v!!, data!!)


        var dialog = AlertDialog.Builder(context)
                .setCancelable(false)
                .setNeutralButton("Delete Subject!", getDialogActions2(data))
                .setPositiveButton("Done ", getDialogActions2(data))
                .setNegativeButton("Cancel ", getDialogActions2(data))
                .setView(v)
                .create()

        dialog.setOnShowListener {

            dialog.getButton(android.support.v7.app.AlertDialog.BUTTON_NEUTRAL)
                    .setTextColor(getColorFromResources(dialog.context, R.color.g_red))
            dialog.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(getColorFromResources(dialog.context, R.color.g_blue))
            dialog.getButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE)
                    .setTextColor(getColorFromResources(dialog.context, R.color.g_blue))

        }
        dialog.show()


    }
    private fun setViewsInternalActions(v: View, data: SubjectModel) {
        Log.e(TAG, "setViewsInternalActions: called with data:$data, view:$v")

        v.al_etSubjectName.setText("${data.subjectName}")
        v.al_etTotal.setText("${data.total}")
        v.al_etAttended.setText("${data.attended}")

        v.al_btAttend.setOnClickListener {
            data.total++
            v.al_etTotal.setText("${data.total}")

            data.attended++
            v.al_etAttended.setText("${data.attended}")
        }
        v.al_btMiss.setOnClickListener {
            data.total++
            v.al_etTotal.setText("${data.total}")
        }

    }
    private fun getDialogActions2(data: SubjectModel? = SubjectModel()): DialogInterface.OnClickListener {
        Log.e(TAG, "[PRIVATE]getDialogActions:called with args:$data")


        return DialogInterface.OnClickListener { dialog, buttonID ->
            dialog.dismiss()

        }
    }


    fun getColorFromResources(context: Context, colorID: Int): Int {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.resources.getColor(colorID, context.theme)
        } else {
            @Suppress("DEPRECATION")
            context.resources.getColor(colorID)
        }

    }
    fun getTextDate(): CharSequence? {

        val df = SimpleDateFormat("EEE, dd MMM yyyy ", Locale.getDefault())
        return df.format(Calendar.getInstance().time)

    }


    object SubjAdapterUtils {
        private const val TAG = "Utils.SubjAdapter"

        @Suppress("DEPRECATION")
        fun vibrate(context: Context, miliSec: Long) {
            // needs system's permission in manifest
            val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(miliSec, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                //deprecated in API 26
                v.vibrate(miliSec)
            }
        }


        fun getPercentage(attended: Int, total: Int): Double {
            if (total == 0) {
                return 0.00
            } else {
                var x = (attended).toDouble() / total.toDouble() * 100.00
                val factor = Math.pow(10.0, 2.00).toLong()

                x *= factor
                return Math.round(x).toDouble() / factor
            }
        }


    }

    //data won't be ever null because we covered it with empty default object and context
    // is always mandatory
    private enum class DialogDataType{FLAG_NEW_DATA,FLAG_OLD_DATA}
    fun test_showSubjectEditorDialog(context: Context,
                                     subjectsManager: SubjectsManager,data: SubjectModel?) {

        if(data==null){
            _test_showSubjectEditorDialog(context, SubjectModel(),subjectsManager,DialogDataType.FLAG_NEW_DATA)
        }
        else{
            _test_showSubjectEditorDialog(context,data,subjectsManager,DialogDataType.FLAG_OLD_DATA)

        }

    }


    private fun _test_showSubjectEditorDialog(ctx: Context, data: SubjectModel
                                              , mngr: SubjectsManager, dataType: DialogDataType) {


        @SuppressLint("InflateParams")      // for null root, not any problem tho: https://stackoverflow.com/questions/24832497
        val v = LayoutInflater
                .from(ctx)
                .inflate(R.layout.al_new_subject, null, false)

        _test_setViewsInternalActions(v, data)



        val dialog = AlertDialog.Builder(ctx)
                .setCancelable(false)
                .setNeutralButton("Delete Subject", _test_getDialogActions2(v,data, mngr,dataType))
                .setPositiveButton("Done ", _test_getDialogActions2(v,data, mngr, dataType))
                .setNegativeButton("Cancel ", _test_getDialogActions2(v,data, mngr, dataType))
                .setView(v)
                .create()

        dialog.setOnShowListener {

            dialog.getButton(android.support.v7.app.AlertDialog.BUTTON_NEUTRAL)
                    .setTextColor(getColorFromResources(dialog.context, R.color.g_red))
            dialog.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(getColorFromResources(dialog.context, R.color.g_blue))
            dialog.getButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE)
                    .setTextColor(getColorFromResources(dialog.context, R.color.g_blue))

        }
        dialog.show()


    }

    private fun _test_setViewsInternalActions(v: View, data: SubjectModel) {
        Log.e(TAG, "setViewsInternalActions: called with data:$data, view:$v")

        v.al_etSubjectName.setText("${data.subjectName}")
        v.al_etTotal.setText("${data.total}")
        v.al_etAttended.setText("${data.attended}")

        v.al_btAttend.setOnClickListener {
            v.al_etTotal.setText((v.al_etTotal.text.toString().toInt() + 1).toString())

            v.al_etAttended.setText((v.al_etAttended.text.toString().toInt() + 1).toString())
        }
        v.al_btMiss.setOnClickListener {
            v.al_etTotal.setText((v.al_etTotal.text.toString().toInt() + 1).toString())


        }

        /* since these changes are not saved anywhere, we have to ''get them from the alert dialogues
         view* when pressed update/delete/modify*/

    }

    private fun _test_getDialogActions2(
            view:View, //view in case the data is modified and we need to get latest changes
            data: SubjectModel = SubjectModel(),//data(old or new)
            mngr: SubjectsManager,
            dataFlag: DialogDataType): DialogInterface.OnClickListener {
        /*NOTE
        we shoud basically use the same flag principle for old or new data, but we are just
           saving the time by including both the data object and the view object

           current case :
           we simply took the whole object's copy with us, along with the custom view, here.
           its the same object that was passed to customView, and since customview can't tamper with
           its id, we  can safely update data by taking the latest value inside the edittexts on
           dialog's button click. the only thing i fear is it might get null pointer since its
           trying to access the data from an active view


           ideal case :
           we should only pass the view and data's id and not complete copy data itself.
           We passed a similar copy of data already in the custom view while  creating the view
           therfore now at the time of saving, we will simply create a new object-> set the values of
           subjname,attended and total by taking the latest values from the edittexts and if
           flagtype == old, set the id we recieved, else leave it blank (indicating that the entry is
           new)

           As i said, we have to pass  "obj_ID: Int?" for getting id if old data is there . but this
           can take null too and thus we have to create checks for that(which by principle seems valid.
           if (your id==null and datatype==new),create a new object with no id ,i.e add new data .
           else{ your data is old, use thae same id and modify.}
           an alternate approch will be to just use "obj_ID: Int" , i.e forced integer which won't take any null
           for new data you will be passing Integer.MAxINT or some other unique flag. at the time
           of new subject obj creation, you will check if (your id==your  and datatype==new),
           create a new object with no id ,i.e add new data .else{ your data is old, use thae same id and modify.}
           but this approach seems less elegant
           {fuck it , both the approaches seems bad}

        * */

        Log.e(TAG, "[PRIVATE]getDialogActions:called with args:$data , $mngr ,$dataFlag ")

        return DialogInterface.OnClickListener { dialog, buttonID ->
            when (buttonID) {

                DialogInterface.BUTTON_POSITIVE -> {
                    //infact, no need to update or even use flag insert will simply handle both
                    // insert or update
                    data.subjectName =view.al_etSubjectName.text.toString()
                    data.total =view.al_etTotal.text.toString().toInt()
                    data.attended = view.al_etAttended.text.toString().toInt()
                    mngr.insertSubjects(data)
                }
                DialogInterface.BUTTON_NEUTRAL ->{
                    if (dataFlag==DialogDataType.FLAG_OLD_DATA){
                        mngr.removeSubjects(data)
                    }
                    else if(dataFlag==DialogDataType.FLAG_NEW_DATA){
                        dialog.dismiss()
                    }

                }
                DialogInterface.BUTTON_NEGATIVE ->{
                    dialog.dismiss()
                }


            }

        }
    }


}