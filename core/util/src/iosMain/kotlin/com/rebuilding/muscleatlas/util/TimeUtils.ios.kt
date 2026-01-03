package com.rebuilding.muscleatlas.util

import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarUnitDay
import platform.Foundation.NSCalendarUnitMonth
import platform.Foundation.NSCalendarUnitYear
import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

actual fun currentTimeMillis(): Long = (NSDate().timeIntervalSince1970 * 1000).toLong()

actual fun formatMillisToDate(timestamp: Long): String {
    return try {
        val date = NSDate(timeIntervalSinceReferenceDate = (timestamp / 1000.0) - 978307200.0)
        val calendar = NSCalendar.currentCalendar
        val components = calendar.components(
            NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitDay,
            fromDate = date
        )
        
        val year = components.year
        val month = components.month.toString().padStart(2, '0')
        val day = components.day.toString().padStart(2, '0')
        
        "$year.$month.$day"
    } catch (e: Exception) {
        ""
    }
}
