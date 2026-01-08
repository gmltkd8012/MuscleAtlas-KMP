package com.rebuilding.muscleatlas.util

import androidx.compose.runtime.Composable

/**
 * 플랫폼별 공유 기능을 제공하는 서비스
 */
expect class ShareService {
    /**
     * 카카오톡으로 공유 (카카오톡 미설치 시 시스템 공유)
     */
    fun shareToKakao(
        title: String,
        description: String,
        imageUrl: String?,
        linkUrl: String,
    )
    
    /**
     * 시스템 기본 공유
     */
    fun shareText(
        title: String,
        text: String,
    )
}

/**
 * Composable에서 ShareService를 사용하기 위한 remember 함수
 */
@Composable
expect fun rememberShareService(): ShareService
