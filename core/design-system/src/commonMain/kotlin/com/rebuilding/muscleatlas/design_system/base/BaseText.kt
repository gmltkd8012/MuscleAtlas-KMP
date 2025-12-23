package com.rebuilding.muscleatlas.design_system.base

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import kotlin.math.max

@Composable
fun BaseText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle,
    color: Color = Color.Black,
    textAlign: TextAlign = TextAlign.Start,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLine: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        style = style,
        color = color,
        modifier = modifier,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLine,
    )
}