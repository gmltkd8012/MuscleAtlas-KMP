package com.rebuilding.muscleatlas.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual class ExternalLinkLauncher {
    actual fun openInstagram(username: String) {
        val instagramAppUrl = NSURL(string = "instagram://user?username=$username")
        val instagramWebUrl = NSURL(string = "https://www.instagram.com/$username")

        // 인스타그램 앱이 설치되어 있는지 확인
        if (instagramAppUrl != null && UIApplication.sharedApplication.canOpenURL(instagramAppUrl)) {
            UIApplication.sharedApplication.openURL(instagramAppUrl)
        } else if (instagramWebUrl != null) {
            // 앱 미설치 시 브라우저로 열기
            UIApplication.sharedApplication.openURL(instagramWebUrl)
        }
    }

    actual fun openUrl(url: String) {
        val nsUrl = NSURL(string = url) ?: return
        if (UIApplication.sharedApplication.canOpenURL(nsUrl)) {
            UIApplication.sharedApplication.openURL(nsUrl)
        }
    }
}

@Composable
actual fun rememberExternalLinkLauncher(): ExternalLinkLauncher {
    return remember { ExternalLinkLauncher() }
}
