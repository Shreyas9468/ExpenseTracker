package com.example.expensetracker.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.expensetracker.data.model.UserInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user_info WHERE id = 1")
    fun getuserinfo(): Flow<UserInfo>

    @Upsert
    suspend fun insertuser(userInfo: UserInfo)
}