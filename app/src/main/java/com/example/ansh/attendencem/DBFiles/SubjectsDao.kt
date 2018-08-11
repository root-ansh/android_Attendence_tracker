package com.example.ansh.attendencem.DBFiles

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.ansh.attendencem.SubjectModel

@Dao
interface SubjectsDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOneOrManySubjects(vararg subject: SubjectModel)


    @Delete
    fun removeOneOrManySubjects(vararg subject: SubjectModel)

    @Query("DELETE FROM AllSubjectstable")
    fun removeAllSubjects()

    @Query("DELETE FROM AllSubjectstable  WHERE _id = :i")
    fun removeSubjectByID(i:Int)


    @Query("SELECT * FROM AllSubjectstable WHERE _id = :subjectID")
    fun getSubjectByID(subjectID:Int): SubjectModel

    @Query("SELECT * FROM AllSubjectstable")
    fun getAllSubjects(): LiveData<List<SubjectModel>>


    @Update
    fun updateSubject(vararg subject: SubjectModel)

}
