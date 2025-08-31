package com.rebuilding.muscleatlas.design_system.base

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rebuilding.muscleatlas.design_system.theme.AppColors

@Composable
fun BaseLine(
    modifier: Modifier = Modifier,
    lineColor: Color,
    lineThickness: Dp = 1.dp,
) {
    Box(modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(lineThickness)
                .align(Alignment.TopCenter)
        ) {
            drawLine(
                color = lineColor,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = lineThickness.toPx()
            )
        }
    }
}

@Preview
@Composable
private fun BaseLinePreview() {
    BaseLine(
        lineColor = AppColors.primaryDark
    )
}