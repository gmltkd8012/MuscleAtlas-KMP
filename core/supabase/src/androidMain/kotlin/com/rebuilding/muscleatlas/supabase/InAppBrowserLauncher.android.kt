package com.rebuilding.muscleatlas.supabase

import dev.yjyoon.kinappbrowser.KInAppBrowser

actual object InAppBrowserLauncher {
    actual fun open(url: String) {
        KInAppBrowser.open(url)
    }
}
