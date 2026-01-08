package com.rebuilding.muscleatlas.util

import androidx.compose.runtime.Composable

/**
 * 외부 앱/브라우저로 링크를 여는 유틸리티
 */
expect class ExternalLinkLauncher {
    /**
     * 인스타그램 프로필 열기
     * - 앱 설치 시: 인스타그램 앱으로 이동
     * - 앱 미설치 시: 브라우저로 이동
     *
     * @param username 인스타그램 사용자명 (@ 제외)
     */
    fun openInstagram(username: String)

    /**
     * URL을 브라우저로 열기
     */
    fun openUrl(url: String)
}

@Composable
expect fun rememberExternalLinkLauncher(): ExternalLinkLauncher
