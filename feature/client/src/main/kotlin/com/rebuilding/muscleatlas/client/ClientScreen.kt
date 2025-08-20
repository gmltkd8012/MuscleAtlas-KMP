package com.rebuilding.muscleatlas.client

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp

@Composable
fun ClientScreen() {
    Box(contentAlignment = Alignment.Center) {
        Text(text = "Client Screen", fontSize = 24.sp)
    }
}