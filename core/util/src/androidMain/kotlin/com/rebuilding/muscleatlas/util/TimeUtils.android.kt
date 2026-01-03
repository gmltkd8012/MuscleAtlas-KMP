package com.rebuilding.muscleatlas.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

actual fun currentTimeMillis(): Long = System.currentTimeMillis()

actual fun formatMillisToDate(timestamp: Long): String {
    return try {
        val sdf = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
        sdf.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        sdf.format(Date(timestamp))
    } catch (e: Exception) {
        ""
    }
}
