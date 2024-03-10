package com.tasks.core.dateformat

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.parseDateToTime(): String? {
    val inputSDF = SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.getDefault())
    val outputSDF = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val date: Date? = try {
        inputSDF.parse(this)
    } catch (e: ParseException) {
        return this
    }
    return date?.let { outputSDF.format(it) }
}