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

data class MovmentBottomSheetData(
    val isShown: Boolean = false,
    val id: String? = null,
    val workoutId: String? = null,
    val imgUrl: String? = null,
    val type: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val currentMills: Long? = null,
)