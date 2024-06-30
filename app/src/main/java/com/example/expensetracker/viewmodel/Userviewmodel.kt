package com.example.expensetracker.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.ExpenseDatabase

import com.example.expensetracker.data.dao.UserDao
import com.example.expensetracker.data.model.UserInfo
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class Userviewmodel(private val userDao: UserDao) : ViewModel() {
    val user = userDao.getuserinfo()

    fun insertName(userInfo: UserInfo) {
        viewModelScope.launch {
            userDao.insertuser(userInfo)
        }
    }
}

class UserViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Userviewmodel::class.java)) {
            val userDao = ExpenseDatabase.getDatabase(context).userDao()
            @Suppress("UNCHECKED_CAST")
            return Userviewmodel(userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
