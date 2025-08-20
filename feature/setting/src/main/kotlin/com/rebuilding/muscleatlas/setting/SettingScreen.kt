package com.rebuilding.muscleatlas.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.rebuilding.muscleatlas.design_system.AppColors

@Composable
fun SettingScreen() {
    Box(
        modifier = Modifier.fillMaxSize().background(AppColors.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Setting Screen", fontSize = 24.sp)
    }
}