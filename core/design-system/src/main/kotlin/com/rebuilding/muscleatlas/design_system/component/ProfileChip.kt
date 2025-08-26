package com.rebuilding.muscleatlas.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.rebuilding.muscleatlas.design_system.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseText
import com.rebuilding.muscleatlas.design_system.base.RoundedAsyncImage
import com.rebuilding.muscleatlas.util.getFirstWord

@Composable
fun ProfileChip(
    modifier: Modifier = Modifier,
    name: String,
    size: Dp = Dp.Unspecified,
    profileImg: String? = null,
) {

    if (profileImg != null) {
        RoundedAsyncImage(
            imageUrl = profileImg,
            modifier = modifier.size(size).clip(CircleShape)
        )
    } else {
        Box(
            modifier = modifier
                .size(size)
                .clip(CircleShape)
                .background(color = AppColors.color.onPrimary.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            BaseText(
                text = name.getFirstWord(),
                style =  MaterialTheme.typography.labelLarge.copy(
                    fontSize = 20.sp,
                    lineHeight = 28.sp,
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Center,
                        trim = LineHeightStyle.Trim.None
                    ),
                    fontWeight = FontWeight(800),
                ),
                modifier = Modifier.alpha(0.2f),
                color = AppColors.color.onSecondary
            )
        }
    }
}