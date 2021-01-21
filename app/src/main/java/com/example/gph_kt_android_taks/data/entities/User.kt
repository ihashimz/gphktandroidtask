package com.example.gph_kt_android_taks.data.entities

import androidx.annotation.Nullable
import androidx.room.*
import java.util.*


@Entity(tableName = "Users", indices = [Index(value = ["email", "identity"], unique = true)])
data class User(
        var id: String = UUID.randomUUID().toString(),
        @ColumnInfo(name = "email")
        var email: String,
        var name: String,
        var phone: String,
        @PrimaryKey@ColumnInfo(name = "identity")
        var identity: String,
        var password: String,
        @Nullable
        var image : ByteArray ? = null

        )

