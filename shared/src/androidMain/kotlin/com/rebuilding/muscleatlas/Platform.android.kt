package com.rebuilding.muscleatlas

import android.os.Build

class AndroidPlatform : Platform {
    override val name: String
        get() = "Android"
    override val storeLink: String?
        get() = "https://play.google.com/store"
}

actual fun getPlatform(): Platform = AndroidPlatform()
