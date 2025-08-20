package com.rebuilding.muscleatlas.setting

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp

@Composable
fun SettingScreen() {
    Box(contentAlignment = Alignment.Center) {
        Text(text = "Setting Screen", fontSize = 24.sp)
    }
}