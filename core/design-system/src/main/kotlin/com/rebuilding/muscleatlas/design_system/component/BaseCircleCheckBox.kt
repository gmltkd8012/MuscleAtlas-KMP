package com.rebuilding.muscleatlas.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rebuilding.muscleatlas.design_system.theme.AppColors

@Composable
fun BaseCircleCheckBox(
    modifier: Modifier = Modifier,
    isChecked: Boolean = false,
) {
    Box(
        modifier = modifier
            .padding(end = 8.dp, top = 8.dp)
            .background(
                color = if (isChecked) AppColors.clear else AppColors.warning,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = if (isChecked) Icons.Default.Check else Icons.Default.Close,
            contentDescription = null,
            tint = AppColors.fixedWithe
        )
    }
}