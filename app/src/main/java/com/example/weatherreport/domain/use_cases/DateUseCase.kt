package com.example.weatherreport.domain.use_cases

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class DateUseCase {

    fun getLocalDate(unixDateTime: Long): Date {
        val pattern = SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.getDefault())
        pattern.timeZone = TimeZone.getDefault()
        return Date((unixDateTime) * 1000)
    }

    fun getDayFromDate(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.DAY_OF_MONTH)
    }

    fun subtractOneDayFromDate(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        return calendar.time
    }

    companion object {
        private const val DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss"
    }
}