package com.rebuilding.muscleatlas.util

import kotlin.js.Date

actual fun currentTimeMillis(): Long = Date.now().toLong()
