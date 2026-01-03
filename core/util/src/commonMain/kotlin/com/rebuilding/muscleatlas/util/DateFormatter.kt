package com.rebuilding.muscleatlas.util

/**
 * 날짜 포맷팅 유틸리티 클래스
 * 한국 시간대(Asia/Seoul) 기준으로 동작
 */
object DateFormatter {
    
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
            val dateStr = formatMillisToDate(timestamp)
            if (dateStr.isEmpty()) return ""
            if (suffix.isNotEmpty()) "$dateStr $suffix" else dateStr
        } catch (e: Exception) {
            ""
        }
    }
    
    /**
     * 현재 시간을 Unix timestamp(밀리초)로 반환
     */
    fun getCurrentTimeMillis(): Long {
        return currentTimeMillis()
    }
}
