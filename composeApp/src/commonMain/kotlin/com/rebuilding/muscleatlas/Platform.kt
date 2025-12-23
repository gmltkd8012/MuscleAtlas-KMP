package com.rebuilding.muscleatlas

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
