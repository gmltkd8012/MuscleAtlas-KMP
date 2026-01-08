package com.rebuilding.muscleatlas.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication

actual class ShareService {
    actual fun shareToKakao(
        title: String,
        description: String,
        imageUrl: String?,
        linkUrl: String,
    ) {
        // iOS에서는 시스템 공유 사용
        // 카카오톡 SDK iOS 연동은 추후 구현
        shareText(title, "$description\n\n$linkUrl")
    }

    actual fun shareText(
        title: String,
        text: String,
    ) {
        val activityItems = listOf(text)
        val activityController = UIActivityViewController(
            activityItems = activityItems,
            applicationActivities = null,
        )

        val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
        rootViewController?.presentViewController(
            activityController,
            animated = true,
            completion = null,
        )
    }
}

@Composable
actual fun rememberShareService(): ShareService {
    return remember { ShareService() }
}
