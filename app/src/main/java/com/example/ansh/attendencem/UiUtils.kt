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
import kotlinx.android.synthetic.main.al_new_subject.view.*
import java.text.SimpleDateFormat
import java.util.*

object UiUtils {
    private val TAG = "UiUTILS"

    @SuppressLint("InflateParams")// for null root, not any problem tho: https://stackoverflow.com/questions/24832497
    fun showSubjectEditorDialog(context: Context, data: SubjectModel? = SubjectModel()) {//data won't be ever null becaue we covered it with empty default object and context is always mandatory
        Log.e(TAG, "showSubjectEditorDialog :called with args:$context,$data")


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



    @Suppress("DEPRECATION")
    fun getColorFromResources(context: Context, colorID: Int): Int {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.resources.getColor(colorID, context.theme)
        } else {
            context.resources.getColor(colorID)
        }

    }
    fun getTextDate(): CharSequence? {

        val df = SimpleDateFormat("EEE, dd MMM yyyy ", Locale.getDefault())
        return df.format(Calendar.getInstance().time)

    }


    object SubjAdapterUtils{
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

}