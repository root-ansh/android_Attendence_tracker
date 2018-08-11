package com.example.ansh.attendencem

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.ansh.attendencem.RvFiles.RvAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() { // god this is too weird , it has changed the perception towards things that  extend vs things that implement

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(supportActionBar!=null) {
            supportActionBar!!.subtitle = UiUtils.getTextDate()
            supportActionBar!!.setBackgroundDrawable(ColorDrawable(UiUtils.getColorFromResources(this@MainActivity, R.color.white)))
        }


        val manager=ViewModelProviders.of(this@MainActivity).get(LiveSubjectsManager::class.java)

        manager.getAllSubjects().observe(this@MainActivity, Observer {allsubjects->

            if(allsubjects==null ||allsubjects.size==0 ){
                placholderMain.visibility=View.VISIBLE
            }
            else{
                placholderMain.visibility=View.GONE

            }
        })

        rvLectures.layoutManager= LinearLayoutManager(this)
        rvLectures.adapter= (RvAdapter(manager,this@MainActivity))

        btAdd.setOnClickListener{
            UiUtils.showSubjectEditorDialog(this@MainActivity,manager,null)

        }



    }
}
/*todo
        *
        * add work manager for periodic reminders to mark attendence
        * UI: every subject should have a fixed color:
        *                       - could store in database and make it user customizable
        *                       - could simply have it stored temorarily in a list of colors in recycler view only
        *                       - make a theme out of it : theme 1 : colorful : every subject has its own color
        *                       - theme 2 plain : every subject has blue color
        * UI : total percentage in collaspable action bar
        * UI: bottom lines
        * preferences : able to set text sizes/ whole object size via settings
        * permissions for  vibration
        * UI : sortable, multi select
        * UI : place holder when data is 0
        * UI: navigation drawer/ about page/settings
        * Ui: delete all button dialog
        *
        *
        *
* */
