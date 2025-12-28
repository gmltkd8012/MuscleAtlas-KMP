package com.rebuilding.muscleatlas.supabase

/**
 * Platform-specific in-app browser launcher
 * Uses Chrome Custom Tabs on Android and SFSafariViewController on iOS
 */
expect object InAppBrowserLauncher {
    fun open(url: String)
}
