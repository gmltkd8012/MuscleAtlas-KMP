package com.rebuilding.muscleatlas.design_system.base


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rebuilding.muscleatlas.design_system.theme.AppColors
import com.rebuilding.muscleatlas.ui.extension.clickableWithoutIndication

@Composable
fun BaseButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    iconSize: Dp,
    iconColor: Color = MaterialTheme.colorScheme.onPrimary,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier.clickableWithoutIndication(
            onClick = onClick
        ),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            modifier = Modifier.size(iconSize),
            imageVector = icon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(iconColor),
            contentScale = ContentScale.Fit
        )
    }
}

@Preview
@Composable
private fun BaseButtonPreview() {
    BaseButton(
        icon = Icons.Default.Add,
        iconSize = 24.dp,
        onClick = {}
    )
}