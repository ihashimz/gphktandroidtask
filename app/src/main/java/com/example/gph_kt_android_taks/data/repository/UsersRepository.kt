package com.example.gph_kt_android_taks.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.gph_kt_android_taks.data.entities.User
import com.example.gph_kt_android_taks.data.local.GPHDatabase

class UsersRepository {
    companion object {
        var localDatabase: GPHDatabase? = null

        var userModel: LiveData<User>? = null

        fun initializeDB(context: Context): GPHDatabase {
            return GPHDatabase.getDatabase(context)
        }

        suspend fun insertUser(context: Context, user: User) {
            localDatabase = initializeDB(context)
            localDatabase!!.users().insertUser(user)

        }

        fun getUser(context: Context,id:String) : LiveData<User> ?{
            localDatabase = initializeDB(context)
            return localDatabase!!.users().getUser(id)

        }

        fun login(context: Context,id:String,password:String) : LiveData<User>{
            localDatabase = initializeDB(context)
            val res = localDatabase!!.users().login(id,password)
            return  res
        }


        fun isUserExist(context: Context,email:String,id:String) : LiveData<List<User>>{
            localDatabase = initializeDB(context)
            return localDatabase!!.users().findByEmailOrIdentity(email,id)

        }
    }

}