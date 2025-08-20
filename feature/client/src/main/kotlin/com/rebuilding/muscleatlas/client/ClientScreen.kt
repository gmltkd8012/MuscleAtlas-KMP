package com.rebuilding.muscleatlas.client

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
fun ClientScreen() {
    Box(
        modifier = Modifier.fillMaxSize().background(AppColors.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Client Screen", fontSize = 24.sp)
    }
}