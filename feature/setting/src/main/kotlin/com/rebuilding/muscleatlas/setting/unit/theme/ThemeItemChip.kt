package com.rebuilding.muscleatlas.setting.unit.theme

import android.R.attr.name
import android.R.attr.onClick
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.test.isSelected
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rebuilding.muscleatlas.design_system.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseText
import com.rebuilding.muscleatlas.design_system.base.RoundedImage
import com.rebuilding.muscleatlas.model.AppTheme
import com.rebuilding.muscleatlas.ui.extension.clickableWithoutIndication

@Composable
fun ThemeItemChip(
    mode: AppTheme,
    selectedMode: String,
    onClick: (String) -> Unit = {},
) {
    val isSelected = mode.id == selectedMode
    val selectedColor = if (isSelected) AppColors.onPrimary else AppColors.onSecondary.copy(alpha = 0.5f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(AppColors.primary)
            .padding(16.dp)
            .clickableWithoutIndication(
                onClick = { onClick(mode.id) }
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                colorFilter = ColorFilter.tint(selectedColor),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(Modifier.width(12.dp))

        BaseText(
            text = mode.title,
            style = MaterialTheme.typography.titleSmall.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            ),
            color = selectedColor
        )
    }
}