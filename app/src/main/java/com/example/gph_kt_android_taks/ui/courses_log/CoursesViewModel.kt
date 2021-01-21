package com.example.gph_kt_android_taks.ui.courses_log

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.gph_kt_android_taks.data.entities.Course
import com.example.gph_kt_android_taks.data.repository.CoursesRepository

class CoursesViewModel : ViewModel() {
   var items : LiveData<List<Course>> ? = null

    fun getAllCourses(context: Context,userId:String):LiveData<List<Course>>?{
        val data = CoursesRepository.getAllCourses(context,userId)
        items = data
        return data
    }

    suspend fun insertCourse(context: Context,course: Course){
        CoursesRepository.insertCourse(context,course)
        getAllCourses(context,course.userId)
    }
}