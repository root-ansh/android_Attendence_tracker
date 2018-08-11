package com.example.ansh.attendencem

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.util.Log
import com.example.ansh.attendencem.DBFiles.AppDB
import com.example.ansh.attendencem.DBFiles.SubjectsDao

class LiveSubjectsManager(application: Application) : AndroidViewModel(application) {
    private val TAG ="$$ SUBJECTS_MANAGER $$"
    private var queryHandler: SubjectsDao? = null

//        constructor(application: Application):super(application) {
//        val dbObject :AppDB = AppDB.getDatabaseInstance(application.applicationContext)
//        this.queryHandler = dbObject.allActions();
//
//    }

//    just a cooler way of writing primary constructor : initialise with super call in the parent c
//     lass itself instead of creating a specific constructor , meh , not cool.
    init {
        Log.e(TAG,"init: called")
        val dbObject: AppDB = AppDB.getDatabaseInstance(application.applicationContext)
        queryHandler = dbObject.allActions()
    }

    // getFunctions

    fun insertSubjects(vararg subjectOrSubjects: SubjectModel) {
        Log.e(TAG,"insertSubjects:called ")
        queryHandler!!.insertOneOrManySubjects(*subjectOrSubjects)
    }

    // remove functions


    fun removeSubjects(vararg subjectOrSubjects: SubjectModel) {
        Log.e(TAG,"removeSubjects: called ")
        queryHandler!!.removeOneOrManySubjects(*subjectOrSubjects)
    }

    //untested
    fun removeAllSubjects() {
        Log.e(TAG,"removeAllSubjects: called")
        queryHandler!!.removeAllSubjects()
    }
    //untested
    fun removeSubjectByID(id: Int) {
        Log.e(TAG,"removeSubjectByID: called")
        queryHandler!!.removeSubjectByID(id)

    }

    fun getAllSubjects(): LiveData<List<SubjectModel>> {
        Log.e(TAG,"getAllSubjects: called")
        return queryHandler!!.getAllSubjects()
    }
    //untested
    fun getSubjectByID(subjectId: Int): SubjectModel {
        Log.e(TAG,"getSubjectByID: called")
        return queryHandler!!.getSubjectByID(subjectId)
    }

    fun update(subjectModel: SubjectModel){
        Log.e(TAG,"update: called")
        return queryHandler!!.updateSubject(subjectModel)
    }


}
