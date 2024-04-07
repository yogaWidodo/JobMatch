package com.capstone.jobmatch.core.utills

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DateFormatter {
    fun formatDate(): String {
        val current = Calendar.getInstance()
        val sdf = SimpleDateFormat("EEE,d MMM yyyy HH:mm a", Locale.getDefault())
        return sdf.format(current.time)
    }
}