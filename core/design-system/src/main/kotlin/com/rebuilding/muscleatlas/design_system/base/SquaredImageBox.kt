package com.rebuilding.muscleatlas.design_system.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rebuilding.muscleatlas.design_system.AppColors
import com.rebuilding.muscleatlas.ui.extension.clickableWithoutIndication

@Composable
fun SquaredImageBox(
    modifier: Modifier = Modifier,
    icon: ImageVector?,
    iconColor: Color = AppColors.onSecondary,
    size: Dp = Dp.Unspecified,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .size(size)
            .background(color = AppColors.onPrimary.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp))
            .clickableWithoutIndication(
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        if (icon != null) {
            Image(
                imageVector = icon,
                contentDescription = null,
                colorFilter = ColorFilter.tint(iconColor),
                contentScale = ContentScale.Fit
            )
        } else {
            BaseText(
                text = "No Image",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 12.sp,
                    fontWeight = FontWeight(500)
                ),
                color = AppColors.onSecondary,
                textAlign = TextAlign.Center
            )
        }
    }

}

@Preview(showBackground = false)
@Composable
private fun SquaredImageBoxPreview() {
    SquaredImageBox(
        icon = Icons.Default.Add,
        size = 100.dp
    )
}