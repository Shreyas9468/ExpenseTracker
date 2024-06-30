package com.example.expensetracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.expensetracker.data.dao.ExpenseDao
import com.example.expensetracker.data.dao.UserDao
import com.example.expensetracker.data.model.ExpenseEntity
import com.example.expensetracker.data.model.UserInfo

@Database(entities = [ExpenseEntity::class, UserInfo::class], version = 1, exportSchema = false)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun userDao(): UserDao

    companion object{
        const val DATABASE_NAME = "expense_database"

        @JvmStatic
        fun getDatabase(context: Context) : ExpenseDatabase {
            return Room.databaseBuilder(
                context,
                ExpenseDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration().build()
        }
    }
}
