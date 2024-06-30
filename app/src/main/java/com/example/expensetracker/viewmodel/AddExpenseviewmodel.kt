package com.example.expensetracker.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.data.ExpenseDatabase
import com.example.expensetracker.data.dao.ExpenseDao
import com.example.expensetracker.data.model.ExpenseEntity
import java.lang.IllegalArgumentException

class AddExpenseviewmodel(private  val dao: ExpenseDao):ViewModel() {
    suspend fun InsertExpense(expenseEntity: ExpenseEntity):Boolean{
        try {
            dao.insertExpense(expenseEntity)
            return true
        }
        catch (ex : Throwable){
            return false
        }
    }


}

class AddExpenseviewmodelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddExpenseviewmodel::class.java)) {
            val dao = ExpenseDatabase.getDatabase(context).expenseDao()
            @Suppress("UNCHECKED_CAST")
            return AddExpenseviewmodel(dao ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}