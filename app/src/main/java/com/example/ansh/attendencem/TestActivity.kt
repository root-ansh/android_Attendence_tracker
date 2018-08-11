package com.example.ansh.attendencem

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.ansh.attendencem.DBFiles.SubjectsManager
import kotlinx.android.synthetic.main.activity_test.*
import java.util.*

class TestActivity : AppCompatActivity() {
    val TAG: String = "TestActivity"
    private var datalistLocalCopy: List<SubjectModel>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)



        Log.e(TAG, "onCreate: called")

        val subjectsManager =
                ViewModelProviders
                        .of(this@TestActivity)
                        .get(SubjectsManager::class.java)

        Log.e(TAG, "--onCreate: created subjects manager")
        enableLiveUpdates(subjectsManager)// this method
        Log.e(TAG, "--onCreate: live updates are now enabled")


        test_btAddNew.setOnClickListener {
            Log.e(TAG, "btAdd new: clicked")

            UiUtils.test_showSubjectEditorDialog(this@TestActivity,subjectsManager,null)
        }

        testbtDeleteEntry.setOnClickListener { view ->
            Log.e(TAG, "onCreate:clicked ")
            val x = test_et_modifypos.text.toString().toIntOrNull()
            Log.e(TAG, "onCreate: position selected : ${x} ")
            if (x != null) {
                Log.e(TAG, "onCreate: current data list local copy = \n\t\t{${datalistLocalCopy} \n}")
                subjectsManager.removeSubjects(datalistLocalCopy?.get(x)!!)
            }
        }

        test_btAttend.setOnClickListener {
            Log.e(TAG, "test_btAttend:clicked ")
            val x = test_et_modifypos.text.toString().toInt()// assuming x is definately not null
            Log.e(TAG, "test_btAttend: position selected : ${x} ")
            val subject = datalistLocalCopy?.get(x)
            Log.e(TAG, "test_btAttend:subject recieved: ${subject} ")
            if (subject != null) {
                subject.total++
                subject.attended++
                subjectsManager.update(subject)

            }
        }

        test_btMiss.setOnClickListener {
            Log.e(TAG, "test_btMiss: ")
            val x = test_et_modifypos.text.toString().toInt()// assuming x is definately not null
            Log.e(TAG, "test_btAttend: position selected : ${x} ")

            val subject = datalistLocalCopy?.get(x)
            Log.e(TAG, "test_btAttend:subject recieved: ${subject} ")


            if (subject != null) {
                subject.total++
                subjectsManager.update(subject)

            }
        }

        test_btshowInEditorDialog.setOnClickListener {
            Log.e(TAG, "test_btshowInEditorDialog: ")
            val x = test_et_modifypos.text.toString().toIntOrNull()
            Log.e(TAG, "test_btshowInEditorDialog: position selected : ${x} ")

            if (x != null) {
                UiUtils.test_showSubjectEditorDialog(this@TestActivity,subjectsManager,datalistLocalCopy?.get(x))
               // UiUtils.test_showSubjectEditorDialog(this@TestActivity, datalistLocalCopy?.get(x),subjectsManager)
            }
        }


    }

    private fun enableLiveUpdates(subjectsManager: SubjectsManager) {
        Log.e(TAG, "enableLiveUpdates: ")


        subjectsManager.getAllSubjects().observe(this@TestActivity, Observer { subjectList ->
            Log.e(TAG, "getAllSubjectsObserver:triggered ")
            Log.e(TAG, "getAllSubjectsObserver:recieved list: ${subjectList} ")

            if (subjectList != null) {

                setOfflineLocalCopy(subjectList)

                if (subjectList.size == 0) {

                    test_SubjectName.setText("list is empty")

                }
                else {

                    test_SubjectName.setText("")

                    for (subject in subjectList) {

                        Log.e(TAG,"getAllSubjectsObserver: called for subject:"+subject)

                        test_SubjectName.setText(""+test_SubjectName.text+ "${subject.toString()}")

                    }
                }
            }
            else{
                Log.e(TAG,"enableLiveUpdates:recieved databaseList is null ")
            }
        })

    }

    private fun setOfflineLocalCopy(subjectList: List<SubjectModel>) {
        Log.e(TAG,"setOfflineLocalCopy:called ")
        datalistLocalCopy=subjectList
    }


}
