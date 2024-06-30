package com.example.expensetracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_info")
data class UserInfo(
    @PrimaryKey(autoGenerate = true) val id: Int=0 ,
    val name : String?,
    val email :String?,
)
