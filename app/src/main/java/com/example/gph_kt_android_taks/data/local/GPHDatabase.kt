package com.example.gph_kt_android_taks.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gph_kt_android_taks.data.dao.CourseDao
import com.example.gph_kt_android_taks.data.dao.UserDao
import com.example.gph_kt_android_taks.data.entities.Course
import com.example.gph_kt_android_taks.data.entities.User


@Database(entities = [User::class,Course::class], version = 1, exportSchema = false)
abstract class GPHDatabase : RoomDatabase() {

    abstract fun users(): UserDao
    abstract fun courses(): CourseDao

    companion object {
        @Volatile
        private var INSTANCE: GPHDatabase? = null


        fun getDatabase(context: Context): GPHDatabase {
            val tempInstance =
                INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GPHDatabase::class.java,
                    "GPH_DB"
                ).fallbackToDestructiveMigration()

                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }



}