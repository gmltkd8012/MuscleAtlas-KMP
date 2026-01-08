package com.rebuilding.muscleatlas.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.browser.window

actual class ExternalLinkLauncher {
    actual fun openInstagram(username: String) {
        val url = "https://www.instagram.com/$username"
        window.open(url, "_blank")
    }

    actual fun openUrl(url: String) {
        window.open(url, "_blank")
    }
}

@Composable
actual fun rememberExternalLinkLauncher(): ExternalLinkLauncher {
    return remember { ExternalLinkLauncher() }
}
