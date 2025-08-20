package com.rebuilding.muscleatlas.design_system.base

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun RoundedAsyncImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    cornerRadius: Int = 10
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius.dp)),
        contentScale = ContentScale.Crop
    )
}