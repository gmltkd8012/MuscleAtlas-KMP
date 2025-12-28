package com.rebuilding.muscleatlas.supabase

import kotlinx.browser.window

actual object InAppBrowserLauncher {
    actual fun open(url: String) {
        window.open(url, "_blank")
    }
}
