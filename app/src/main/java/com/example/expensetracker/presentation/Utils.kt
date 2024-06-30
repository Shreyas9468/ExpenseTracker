package com.example.expensetracker.presentation

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.logging.SimpleFormatter

object Utils {
    fun formatDatatoHumanReadableForm(date : Long) : String{
        val dateFormatter = SimpleDateFormat("dd/MM/YYYY" , Locale.getDefault())
        return dateFormatter.format(date)
    }
    fun formatDayMonth(dateInMillis: Long): String {
        val dateFormatter = SimpleDateFormat("dd/MMM", Locale.getDefault())
        return dateFormatter.format(dateInMillis)
    }

    fun formatToDecimalValue(d: Double): String {
        return String.format("%.2f", d)
    }


    fun getMillisFromDate(date: String): Long {
        return getMilliFromDate(date)
    }
    fun getMilliFromDate(dateFormat: String?): Long {
        var date = Date()
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        try {
            date = formatter.parse(dateFormat)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        println("Today is $date")
        return date.time
    }
}