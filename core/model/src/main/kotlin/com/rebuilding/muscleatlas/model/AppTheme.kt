package com.rebuilding.muscleatlas.model

import com.rebuilding.muscleatlas.util.DataStoreUtils

sealed class AppTheme (
    val id: String,
    val title: String,
) {
    data object Light: AppTheme(
        id = DataStoreUtils.ValueConst.LIGHT_THEME,
        title = "라이트 모드"
    )

    data object Dark: AppTheme(
        id = DataStoreUtils.ValueConst.DARK_THEME,
        title = "다크 모드"
    )
}