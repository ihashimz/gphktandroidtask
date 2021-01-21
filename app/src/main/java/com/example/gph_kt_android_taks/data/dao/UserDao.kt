package com.example.gph_kt_android_taks.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gph_kt_android_taks.data.entities.User


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM Users WHERE identity =:identity")
    fun getUser(identity: String) : LiveData<User>

    @Query("SELECT * FROM Users WHERE identity =:identity AND password=:password")
    fun login(identity: String,password:String) : LiveData<User>

    @Query("SELECT * FROM Users WHERE email = :email OR identity = :id")
    fun findByEmailOrIdentity(email: String, id: String): LiveData<List<User>>
}