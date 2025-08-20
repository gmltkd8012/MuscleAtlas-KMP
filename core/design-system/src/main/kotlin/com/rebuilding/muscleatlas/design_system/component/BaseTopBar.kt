package com.rebuilding.muscleatlas.design_system.component

import android.R.attr.navigationIcon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BaseTopBar(
    title: @Composable () -> Unit = {},
    backIcon: @Composable (() -> Unit)? = {},
    endIcon: @Composable (() -> Unit) = {},
    backgroundColor: Color = Color.White,
    contentColor: Color = Color.Black,
) {
    val startPadding = if (backIcon != null) 4.dp else 16.dp

    Surface(
        modifier = Modifier.systemBarsPadding(),
        color = backgroundColor,
        contentColor = contentColor,
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(start = startPadding, end = 16.dp)
                .padding(vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.align(Alignment.CenterStart),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        backIcon.takeIf { it != null }?.let { it() }
                        title()
                    }
                }

                Box(
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        endIcon()
                    }
                }
            }
        }
    }
}