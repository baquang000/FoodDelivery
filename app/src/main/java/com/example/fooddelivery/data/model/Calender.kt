package com.example.fooddelivery.data.model

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Calender {
    fun getCalender(): String {
        val currentTimeMillis = System.currentTimeMillis()
        val dateFomat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ROOT)
        val calender = Calendar.getInstance()
        calender.timeInMillis = currentTimeMillis
        val formattedDateAndTime = dateFomat.format(calender.time)
        return formattedDateAndTime
    }
    fun getTimePlusOneHour(): String {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY) + 1
        val minute = calendar.get(Calendar.MINUTE)
        return "$hour:$minute"
    }
}