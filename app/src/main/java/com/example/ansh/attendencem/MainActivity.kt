package com.example.ansh.attendencem

import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import com.example.ansh.attendencem.RvFiles.RvAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.al_new_subject.view.*
import java.util.*


class MainActivity : AppCompatActivity() { // god this is too weird , it has changed the perception towards things that  extend vs things that implement

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(supportActionBar!=null) {
            supportActionBar!!.subtitle = UiUtils.getTextDate()
            supportActionBar!!.setBackgroundDrawable(ColorDrawable(UiUtils.getColorFromResources(this@MainActivity, R.color.white)))
        }
        //todo add work manager
        rvLectures.layoutManager= LinearLayoutManager(this)
        rvLectures.adapter= RvAdapter(getDatabaseList())

        btAdd.setOnClickListener{
            UiUtils.showSubjectEditorDialog(this@MainActivity)

        }

    }


    private fun getDatabaseList(): LinkedList<SubjectModel> {
        val data :LinkedList<SubjectModel> = LinkedList()

        var i=0
        data.add(SubjectModel("Communication system and bakchodi pado dher saari maa ki aank",6,10));
        data.add(SubjectModel("Subject"+i++,0,0))
        data.add(SubjectModel("Subject"+i++,Random().nextInt(100),Random().nextInt(100)))
        data.add(SubjectModel("Subject"+i++,Random().nextInt(100),Random().nextInt(100)))
        data.add(SubjectModel("Subject"+i++,Random().nextInt(100),Random().nextInt(100)))
        data.add(SubjectModel("Subject"+i++,Random().nextInt(100),Random().nextInt(100)))
        data.add(SubjectModel("Subject"+i++,Random().nextInt(100),Random().nextInt(100)))
        data.add(SubjectModel("Subject"+i++,Random().nextInt(100),Random().nextInt(100)))
        data.add(SubjectModel("Subject"+i++,Random().nextInt(100),Random().nextInt(100)))
        data.add(SubjectModel("Subject"+i++,Random().nextInt(100),Random().nextInt(100)))
        data.add(SubjectModel("Subject"+i++,Random().nextInt(100),Random().nextInt(100)))
        data.add(SubjectModel("Subject"+i++,Random().nextInt(100),Random().nextInt(100)))
        data.add(SubjectModel("Subject"+i++,Random().nextInt(100),Random().nextInt(100)))
        data.add(SubjectModel("Subject"+i++,Random().nextInt(100),Random().nextInt(100)))
        data.add(SubjectModel("Subject"+i++,Random().nextInt(100),Random().nextInt(100)))
        data.add(SubjectModel("Subject"+i++,Random().nextInt(100),Random().nextInt(100)))
        data.add(SubjectModel("Subject"+i++,Random().nextInt(100),Random().nextInt(100)))
        data.add(SubjectModel("Subject"+i++,Random().nextInt(100),Random().nextInt(100)))
        data.add(SubjectModel("Subject"+i++,Random().nextInt(100),Random().nextInt(100)))
        data.add(SubjectModel("Subject"+i++,Random().nextInt(100),Random().nextInt(100)))
        data.add(SubjectModel("Subject"+i++,Random().nextInt(100),Random().nextInt(100)))
        data.add(SubjectModel("Subject"+i++,Random().nextInt(100),Random().nextInt(100)))
        data.add(SubjectModel("Subject"+i++,Random().nextInt(100),Random().nextInt(100)))
        data.add(SubjectModel("Subject"+i++,Random().nextInt(100),Random().nextInt(100)))
        data.add(SubjectModel("Subject"+i++,Random().nextInt(100),Random().nextInt(100)))
        data.add(SubjectModel("Subject"+i++,Random().nextInt(100),Random().nextInt(100)))
        data.add(SubjectModel("Subject"+i++,Random().nextInt(100),Random().nextInt(100)))
        data.add(SubjectModel("Subject"+i++,Random().nextInt(100),Random().nextInt(100)))
        data.add(SubjectModel("Subject"+i++,Random().nextInt(100),Random().nextInt(100)))
        data.add(SubjectModel("Subject"+i++,Random().nextInt(100),Random().nextInt(100)))
        data.add(SubjectModel("Subject"+i,Random().nextInt(100),Random().nextInt(100)))

        return data

    }



    companion object {
          fun showDialog(context:Context,data:SubjectModel= SubjectModel()){

              val v = LayoutInflater.from(context).inflate(R.layout.al_new_subject,null,false)

              //todo add Percentage meter with cool ui
              v.al_etSubjectName.setText(data.subjectName)
              v.al_etAttended.setText("""${data.attended}""")
              v.al_etTotal.setText("""${data.total}""")

              v.al_btAttend.setOnClickListener{

                  data.attended++
                  data.total++

//                  v.et_AlAttended.setText(data.attended)
//                  v.et_AlTotal.setText(data.total)

                          //MyApplication.subjectsManager!!.update(data)


              }
              v.al_btMiss.setOnClickListener{

                  data.total++

//                  v.et_AlAttended.setText(data.attended)
//                  v.et_AlTotal.setText(data.total)
                  //MyApplication.subjectsManager!!.update(data)

              }

               val dialog = AlertDialog.Builder(context)
                       .setNeutralButton("Delete Subject!", getDialogActions2(data))
                       .setPositiveButton("Done ",getDialogActions2(data))
                       .setNegativeButton("Cancel ", getDialogActions2(data))
                       .setView(v)
                       .create()

              dialog.setOnShowListener{
                  dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(dialog.context.resources.getColor(R.color.g_red))

              }
              dialog.show()
          }

        private fun getDialogActions1(data: SubjectModel): View.OnClickListener {
            return   View.OnClickListener {
                if (it.id == R.id.al_btAttend) {
                    data.attended++
                    data.total++

                    it.al_etAttended.setText(data.attended)
                    it.al_etTotal.setText(data.total)



                    //MyApplication.subjectsManager!!.update(data)

                } else if (it.id == R.id.al_btMiss) {

                    data.total++

                    it.al_etAttended.setText(data.attended)
                    it.al_etTotal.setText(data.total)

                    //MyApplication.subjectsManager!!.update(data)

                }
            }
        }

        private fun getDialogActions2(data: SubjectModel): DialogInterface.OnClickListener {

            return DialogInterface.OnClickListener() {dialog, buttonID ->
                if (buttonID==AlertDialog.BUTTON_NEUTRAL){
                    //todo
                }
                else if(buttonID == AlertDialog.BUTTON_NEGATIVE){

                }
                else if(buttonID==AlertDialog.BUTTON_POSITIVE){

                }

            }

        }
    }




}
