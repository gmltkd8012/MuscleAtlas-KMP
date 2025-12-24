package com.rebuilding.muscleatlas

interface Platform {
    val name: String
    val storeLink: String?
}

expect fun getPlatform(): Platform
