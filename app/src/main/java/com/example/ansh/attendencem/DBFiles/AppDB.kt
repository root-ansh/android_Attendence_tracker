package com.example.ansh.attendencem.DBFiles

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.ansh.attendencem.SubjectModel

@Database(entities = arrayOf(SubjectModel::class), version = 1)
abstract class AppDB private constructor(): RoomDatabase() {

    abstract fun allActions(): SubjectsDao

    companion object {

        private var INSTANCE: AppDB? = null

        fun getDatabaseInstance(context: Context): AppDB {
            if (INSTANCE == null) {
                synchronized(AppDB::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDB::class.java,DBConstants.DBNAME)
                                .allowMainThreadQueries()
                                .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }

}