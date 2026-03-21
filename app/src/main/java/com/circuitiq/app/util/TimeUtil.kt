package com.circuitiq.app.util

import java.util.Calendar

fun getGreeting(): String {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return when (hour) {
        in 5..11  -> "Good Morning ☀️"
        in 12..16 -> "Good Afternoon 🌤️"
        in 17..20 -> "Good Evening 🌆"
        else      -> "Good Night 🌙"
    }
}

fun getGreetingIconRes(): Int {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return when (hour) {
        in 5..16  -> com.circuitiq.app.R.drawable.ic_sun
        in 17..20 -> com.circuitiq.app.R.drawable.ic_sunset
        else      -> com.circuitiq.app.R.drawable.ic_moon
    }
}
