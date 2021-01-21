package com.example.gph_kt_android_taks.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "Courses",foreignKeys = [
    ForeignKey(entity = User::class,
        parentColumns = ["identity"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE)])
data class Course(
    @PrimaryKey(autoGenerate = true)
    val id: Long ,
    @ColumnInfo(name = "user_id")
    val userId:String,
    @ColumnInfo(name = "course_name")
    var courseName: String? = null,
    var provider: String? = null,
    var duration: Int? = null,
    var rate: String? = null,
    var city: String? = null,
    var date: String? = null
)
