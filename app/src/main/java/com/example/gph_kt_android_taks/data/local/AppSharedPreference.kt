package com.example.gph_kt_android_taks.data.local

import android.content.Context
import com.example.gph_kt_android_taks.data.entities.User
import com.google.gson.Gson


object AppSharedPreferences {
    private val TAG = "AppSharedPreferences"



    fun storeLoggedUser(context: Context, user: User) {
        val editor = context.getSharedPreferences("USER_ACCOUNT", Context.MODE_PRIVATE).edit()
        editor.putString("User", Gson().toJson(user))
        editor.apply()
    }

    // get user token
    fun getLoggedUser(context: Context): User? {
        val sharedPreferences = context.getSharedPreferences("USER_ACCOUNT", Context.MODE_PRIVATE)
        val user = sharedPreferences.getString("User", null)
        return if (user != null) {
            val account: User = Gson().fromJson(user!!, User::class.java)
            account

        } else null
    }

    fun clearLoggedUser(context: Context) {
        val editor = context.getSharedPreferences("USER_ACCOUNT", Context.MODE_PRIVATE).edit()
        editor.clear()
        editor.apply()
        editor.commit()
    }
}