package com.rebuilding.muscleatlas.design_system.base



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import com.rebuilding.muscleatlas.design_system.theme.AppColors

@Composable
fun RoundedImage(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    iconColor: Color = MaterialTheme.colorScheme.onSecondary,
    size: Dp = Dp.Unspecified
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.1f)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            imageVector = icon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(iconColor),
            contentScale = ContentScale.Fit
        )
    }
}