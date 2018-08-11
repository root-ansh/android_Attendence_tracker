package com.example.ansh.attendencem.DBFiles

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.ansh.attendencem.SubjectModel

class SubjectsManager(application: Application) : AndroidViewModel(application) {
    private var queryHandler: SubjectsDao? = null

//        constructor(application: Application):super(application) {
//        val dbObject :AppDB = AppDB.getDatabaseInstance(application.applicationContext)
//        this.queryHandler = dbObject.allActions();
//
//    }

//    just a cooler way of writing primary constructor : initialise with super call in the parent c
//     lass itself instead of creating a specific constructor , meh , not cool.
    init {
        val dbObject: AppDB = AppDB.getDatabaseInstance(application.applicationContext)
        queryHandler = dbObject.allActions()
    }

    // getFunctions

    fun insertSubjects(vararg subjectOrSubjects: SubjectModel) {
        queryHandler!!.insertOneOrManySubjects(*subjectOrSubjects)
    }

    // remove functions

    fun removeSubjects(vararg subjectOrSubjects: SubjectModel) {
        queryHandler!!.removeOneOrManySubjects(*subjectOrSubjects)
    }

    fun removeAllSubjects() {
        queryHandler!!.removeAllSubjects()
    }

    fun removeSubjectByID(id: Int) {
        queryHandler!!.removeSubjectByID(id)

    }

    fun getAllSubjects(): LiveData<List<SubjectModel>> {
        return queryHandler!!.getAllSubjects()
    }

    fun getSubjectByID(subjectId: Int): SubjectModel {
        return queryHandler!!.getSubjectByID(subjectId)
    }

    fun update(subjectModel: SubjectModel){
        return queryHandler!!.updateSubject(subjectModel)
    }


}
