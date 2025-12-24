package com.rebuilding.muscleatlas

import platform.UIKit.UIDevice

class IOSPlatform : Platform {
    override val name: String
        get() = "iOS"
    override val storeLink: String?
        get() = "https://www.apple.com/app-store/"
}

actual fun getPlatform(): Platform = IOSPlatform()
