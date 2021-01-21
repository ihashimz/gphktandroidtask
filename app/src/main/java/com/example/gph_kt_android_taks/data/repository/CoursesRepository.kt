package com.example.gph_kt_android_taks.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.gph_kt_android_taks.data.entities.Course
import com.example.gph_kt_android_taks.data.entities.User
import com.example.gph_kt_android_taks.data.local.GPHDatabase

class CoursesRepository {
    companion object {

        var localDatabase: GPHDatabase? = null

        var coursesModel: LiveData<List<Course>>? = null

        fun initializeDB(context: Context): GPHDatabase {
            return GPHDatabase.getDatabase(context)
        }

        suspend fun insertCourse(context: Context, course: Course) {
            localDatabase = initializeDB(context)
            localDatabase!!.courses().insertCourse(course)

        }

        fun getAllCourses(context: Context, userId:String) : LiveData<List<Course>> {
            localDatabase = initializeDB(context)
            coursesModel =  localDatabase!!.courses().getUserCourses(userId)
            return coursesModel!!
        }
    }


}