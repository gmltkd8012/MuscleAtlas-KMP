package com.rebuilding.muscleatlas.supabase

import java.awt.Desktop
import java.net.URI

actual object InAppBrowserLauncher {
    actual fun open(url: String) {
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(URI(url))
        }
    }
}
