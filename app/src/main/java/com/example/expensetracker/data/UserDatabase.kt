//package com.example.expensetracker.data
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import com.example.expensetracker.data.ExpenseDatabase.Companion.DATABASE_NAME
//import com.example.expensetracker.data.dao.UserDao
//import com.example.expensetracker.data.model.UserInfo
//
//@Database(entities = [UserInfo::class] , version = 1)abstract class UserDatabase : RoomDatabase(){
//    abstract fun userDao() : UserDao
//
//    companion object {
//        const val Database_Name = "user_info"
//        @JvmStatic
//        fun getDatabase(context: Context) : UserDatabase{
//            return Room.databaseBuilder(
//                context = context,
//                UserDatabase::class.java,
//                DATABASE_NAME
//            ).build()
//        }
//    }
//}