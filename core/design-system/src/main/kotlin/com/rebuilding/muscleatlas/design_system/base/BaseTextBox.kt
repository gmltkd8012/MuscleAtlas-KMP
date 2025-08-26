package com.rebuilding.muscleatlas.design_system.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rebuilding.muscleatlas.design_system.AppColors

@Composable
fun BaseTextBox(
    modifier: Modifier = Modifier,
    description: String,
) {
    Box(
        modifier = modifier
            .background(color = AppColors.color.onPrimary.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp))
            .padding(12.dp),
    ) {
        BaseText(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            color = AppColors.color.onSecondary
        )
    }
}