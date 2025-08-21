package com.rebuilding.muscleatlas.client.unit

import android.R.attr.name
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rebuilding.muscleatlas.design_system.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseText
import com.rebuilding.muscleatlas.design_system.base.SquaredImageBox

@Composable
fun WorkoutBox(
    name: String,
    icon: ImageVector,
    size: Dp = 200.dp,
) {
    Column(
        modifier = Modifier.wrapContentSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SquaredImageBox(
            icon = icon,
            size = size,
        )

        Spacer(Modifier.height(8.dp))

        BaseText(
            text = name,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 16.sp,
            ),
            color = AppColors.onSecondary
        )
    }
}