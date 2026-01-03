package com.rebuilding.muscleatlas.util

import kotlin.js.Date

actual fun currentTimeMillis(): Long = Date.now().toLong()

actual fun formatMillisToDate(timestamp: Long): String {
    return try {
        val date = Date(timestamp.toDouble())
        val year = date.getFullYear()
        val month = (date.getMonth() + 1).toString().padStart(2, '0')
        val day = date.getDate().toString().padStart(2, '0')
        "$year.$month.$day"
    } catch (e: Exception) {
        ""
    }
}
