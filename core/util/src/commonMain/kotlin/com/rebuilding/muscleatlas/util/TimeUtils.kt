package com.rebuilding.muscleatlas.util

/**
 * 플랫폼별 현재 시간을 밀리초로 반환
 */
expect fun currentTimeMillis(): Long

/**
 * Unix timestamp(밀리초)를 "yyyy.MM.dd" 형식으로 변환 (한국 시간대)
 */
expect fun formatMillisToDate(timestamp: Long): String
