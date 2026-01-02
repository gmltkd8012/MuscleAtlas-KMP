package com.rebuilding.muscleatlas.util

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * 날짜 포맷팅 유틸리티 클래스
 * 한국 시간대(Asia/Seoul) 기준으로 동작
 */
object DateFormatter {
    
    private val koreaTimeZone = TimeZone.of("Asia/Seoul")
    
    // ==================== Long (Unix Timestamp) 기반 ====================
    
    /**
     * Unix timestamp(밀리초)를 "2026.01.02 방문" 형식으로 변환
     * 
     * @param timestamp Unix timestamp (밀리초)
     * @param suffix 접미사 (기본값: "방문")
     * @return 포맷된 날짜 문자열 (예: "2026.01.02 방문")
     */
    fun formatMillisToKoreanDate(timestamp: Long?, suffix: String = "방문"): String {
        if (timestamp == null || timestamp == 0L) return ""
        
        return try {
            val instant = Instant.fromEpochMilliseconds(timestamp)
            val localDateTime = instant.toLocalDateTime(koreaTimeZone)
            
            val year = localDateTime.year
            val month = localDateTime.monthNumber.toString().padStart(2, '0')
            val day = localDateTime.dayOfMonth.toString().padStart(2, '0')
            
            if (suffix.isNotEmpty()) "$year.$month.$day $suffix" else "$year.$month.$day"
        } catch (e: Exception) {
            ""
        }
    }
    
    /**
     * Unix timestamp(밀리초)를 "2026.01.02" 형식으로 변환 (접미사 없음)
     */
    fun formatMillisToDate(timestamp: Long?): String {
        return formatMillisToKoreanDate(timestamp, "")
    }
    
    /**
     * 현재 시간을 Unix timestamp(밀리초)로 반환
     */
    fun getCurrentTimeMillis(): Long {
        return Clock.System.now().toEpochMilliseconds()
    }
    
    // ==================== String (ISO 8601) 기반 ====================
    
    /**
     * ISO 8601 형식의 날짜 문자열을 "2026.01.02 방문" 형식으로 변환
     * 
     * @param isoDateString ISO 8601 형식의 날짜 문자열 (예: "2026-01-02T15:30:00+09:00")
     * @param suffix 접미사 (기본값: "방문")
     * @return 포맷된 날짜 문자열 (예: "2026.01.02 방문")
     */
    fun formatToKoreanDate(isoDateString: String?, suffix: String = "방문"): String {
        if (isoDateString.isNullOrBlank()) return ""
        
        return try {
            val instant = Instant.parse(isoDateString)
            val localDateTime = instant.toLocalDateTime(koreaTimeZone)
            
            val year = localDateTime.year
            val month = localDateTime.monthNumber.toString().padStart(2, '0')
            val day = localDateTime.dayOfMonth.toString().padStart(2, '0')
            
            if (suffix.isNotEmpty()) "$year.$month.$day $suffix" else "$year.$month.$day"
        } catch (e: Exception) {
            // 파싱 실패 시 간단한 형식으로 시도
            trySimpleParse(isoDateString, suffix)
        }
    }
    
    /**
     * ISO 8601 형식의 날짜 문자열을 "2026.01.02" 형식으로 변환 (접미사 없음)
     */
    fun formatToDate(isoDateString: String?): String {
        return formatToKoreanDate(isoDateString, "")
    }
    
    /**
     * 현재 시간을 ISO 8601 형식으로 반환
     */
    fun getCurrentIsoDateTime(): String {
        return Clock.System.now().toString()
    }
    
    /**
     * 현재 시간을 "2026.01.02" 형식으로 반환 (한국 시간대)
     */
    fun getCurrentFormattedDate(): String {
        val now = Clock.System.now()
        val localDateTime = now.toLocalDateTime(koreaTimeZone)
        
        val year = localDateTime.year
        val month = localDateTime.monthNumber.toString().padStart(2, '0')
        val day = localDateTime.dayOfMonth.toString().padStart(2, '0')
        
        return "$year.$month.$day"
    }
    
    /**
     * 간단한 날짜 파싱 (ISO 8601 파싱 실패 시 대체)
     * "2026-01-02T..." 형식에서 날짜 부분만 추출
     */
    private fun trySimpleParse(dateString: String, suffix: String): String {
        return try {
            val datePart = dateString.split("T").firstOrNull() ?: dateString
            val parts = datePart.split("-")
            
            if (parts.size >= 3) {
                val formatted = "${parts[0]}.${parts[1]}.${parts[2]}"
                if (suffix.isNotEmpty()) "$formatted $suffix" else formatted
            } else {
                dateString
            }
        } catch (e: Exception) {
            dateString
        }
    }
}
