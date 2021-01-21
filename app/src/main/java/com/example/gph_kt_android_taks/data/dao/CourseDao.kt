package com.example.gph_kt_android_taks.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gph_kt_android_taks.data.entities.Course
import com.example.gph_kt_android_taks.data.entities.User

@Dao
interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCourse(course: Course)

    @Query("SELECT * FROM Courses WHERE user_id =:userId")
    fun getUserCourses(userId: String?) : LiveData<List<Course>>
}