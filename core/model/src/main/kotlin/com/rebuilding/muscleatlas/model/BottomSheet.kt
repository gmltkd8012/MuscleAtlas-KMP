package com.rebuilding.muscleatlas.model

import androidx.compose.runtime.MutableState

data class ClientBottomSheetData(
    val isShown: Boolean = false,
    val imgUrl: String? = null,
    val id: String? = null,
    val name: String? = null,
    val memo: String? = null,
    val currentMills: Long? = null,
)