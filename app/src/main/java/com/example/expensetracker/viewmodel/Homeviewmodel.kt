package com.example.expensetracker.viewmodel

import android.content.Context
import androidx.compose.runtime.Composable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.expensetracker.R
import com.example.expensetracker.data.ExpenseDatabase

import com.example.expensetracker.data.dao.ExpenseDao
import com.example.expensetracker.data.dao.UserDao
import com.example.expensetracker.data.model.ExpenseEntity
import com.example.expensetracker.data.model.UserInfo
import com.example.expensetracker.presentation.Utils
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class Homeviewmodel( dao: ExpenseDao):ViewModel() {

    val expenses = dao.getAllExpense()

    fun getBalance(list: List<ExpenseEntity>): String {
        var balance = 0.0
        for (expense in list) {
            if (expense.type == "Income") {
                balance += expense.amount
            } else {
                balance -= expense.amount
            }
        }
        return "$ ${Utils.formatToDecimalValue(balance)}"
    }

    fun getTotalExpense(list: List<ExpenseEntity>): String {
        var total = 0.0
        for (expense in list) {
            if(expense.type == "Expense"){
                total += expense.amount
            }
        }

        return "$ ${Utils.formatToDecimalValue(total)}"
    }

    fun getTotalIncome(list: List<ExpenseEntity>): String {
        var totalIncome = 0.0
        for (expense in list) {
            if (expense.type == "Income") {
                totalIncome += expense.amount
            }
        }

        return "$ ${Utils.formatToDecimalValue(totalIncome)}"
    }

    fun getItemIcon(item: ExpenseEntity): Int {
        return if (item.category == "Paypal") {
            R.drawable.ic_paypal
        } else if (item.category == "Netflix") {
            R.drawable.ic_netflix
        } else if (item.category == "Starbucks") {
            R.drawable.ic_starbucks
        } else {
            R.drawable.ic_upwork
        }
    }



}
class HomeViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Homeviewmodel::class.java)) {
            val dao = ExpenseDatabase.getDatabase(context).expenseDao()
            @Suppress("UNCHECKED_CAST")
            return Homeviewmodel(dao ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}




