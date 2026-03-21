package com.circuitiq.app.util

import java.util.Calendar

data class GreetingInfo(
    val text: String,
    val iconRes: Int,
    val isAnimated: Boolean = false
)

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
        in 5..8   -> com.circuitiq.app.R.drawable.ic_sun      // Rising sun
        in 9..11  -> com.circuitiq.app.R.drawable.ic_sun      // Full sun
        in 12..16 -> com.circuitiq.app.R.drawable.ic_sun      // Afternoon sun
        in 17..20 -> com.circuitiq.app.R.drawable.ic_sunset   // Evening/sunset
        in 21..23 -> com.circuitiq.app.R.drawable.ic_stars    // Night stars
        else      -> com.circuitiq.app.R.drawable.ic_moon     // Late night moon
    }
}
