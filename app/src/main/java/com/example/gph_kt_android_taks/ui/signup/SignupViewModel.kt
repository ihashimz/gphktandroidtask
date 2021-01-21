package com.example.gph_kt_android_taks.ui.signup

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gph_kt_android_taks.data.entities.User
import com.example.gph_kt_android_taks.data.repository.UsersRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class SignupViewModel : ViewModel() {

    var user : LiveData<User>? = null
    var signUpError : MutableLiveData<String> = MutableLiveData()

    suspend fun insertUser(context: Context, newUser:User){
        try {
            UsersRepository.insertUser(context,newUser)
            signUpError.value = "SUCCESS"

        } catch (e:Exception){
            signUpError.value = "ERROR"
        }
    }
    fun getUser(context: Context, _user:User): LiveData<User>?{

        user = UsersRepository.getUser(context,_user.identity)
        return user
    }
}