package com.rebuilding.muscleatlas.design_system.component

import android.R.attr.onClick
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rebuilding.muscleatlas.design_system.base.BaseText
import com.rebuilding.muscleatlas.ui.extension.clickableWithoutIndication

@Composable
fun NormalButton(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: Int = 18,
    backgroundColor: Color = MaterialTheme.colorScheme.onSecondary,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .clickableWithoutIndication {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        BaseText(
            text = text,
            style =  MaterialTheme.typography.titleMedium.copy(
                fontSize = fontSize.sp,
                fontWeight = FontWeight.Bold,
            ),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}