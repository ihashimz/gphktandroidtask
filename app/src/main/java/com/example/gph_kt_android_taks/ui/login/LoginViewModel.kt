package com.example.gph_kt_android_taks.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gph_kt_android_taks.data.entities.User
import com.example.gph_kt_android_taks.data.repository.UsersRepository

class LoginViewModel : ViewModel() {
    var user :   LiveData<User>? = null

    fun getUser(context: Context, id: String , password:String) : LiveData<User>? {
        user = UsersRepository.login(context,id,password)
        return user
    }
}