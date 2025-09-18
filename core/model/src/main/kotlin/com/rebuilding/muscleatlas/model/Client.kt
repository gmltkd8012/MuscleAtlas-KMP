package com.rebuilding.muscleatlas.model

import android.net.Uri
import java.util.UUID

data class Client(
    val id: String = "",
    val imgUrl: Uri? = null,
    val name: String = "",
    val memo: String = "",
    val currentMills: Long = 0L,
)