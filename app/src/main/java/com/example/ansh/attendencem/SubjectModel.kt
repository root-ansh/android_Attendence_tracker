package com.example.ansh.attendencem

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "AllSubjectstable")
class SubjectModel {

    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0
    var subjectName: String? = ""//null, when testing
    var attended: Int = 0
    var total: Int = 0

    constructor()

    constructor(subjectName: String, attended: Int, total: Int) {
        this.subjectName = subjectName
        this.attended = attended
        this.total = total
    }

    constructor(_id: Long, subjectName: String, attended: Int, total: Int) {
        this._id = _id
        this.subjectName = subjectName
        this.attended = attended
        this.total = total
    }

    override fun toString(): String {
        return "test{" +
                "id=" + _id +
                ", subjectName='" + subjectName + '\''.toString() +
                ", attended=" + attended +
                ", total=" + total +
                '}'
    }
}
