package com.rebuilding.muscleatlas

class WasmPlatform : Platform {
    override val name: String
        get() = "Web with Kotlin/Wasm"
    override val storeLink: String?
        get() = null
}

actual fun getPlatform(): Platform = WasmPlatform()
