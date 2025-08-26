package com.rebuilding.muscleatlas.setting.unit.workoutmanage

import android.R.attr.name
import android.R.attr.onClick
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rebuilding.muscleatlas.design_system.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseText
import com.rebuilding.muscleatlas.design_system.base.SquaredImageBox
import com.rebuilding.muscleatlas.design_system.component.ProfileChip
import com.rebuilding.muscleatlas.ui.extension.clickableWithoutIndication

@Composable
fun WorkoutManageChip(
    modifier: Modifier = Modifier,
    name: String,
    workoutImg: ImageVector? = null,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(AppColors.color.primary)
            .clickableWithoutIndication(
                onClick = onClick
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SquaredImageBox(
                size = 48.dp,
                icon = workoutImg
            )

            Spacer(Modifier.width(16.dp))

            BaseText(
                text = name,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = AppColors.color.onPrimary
            )
        }

        Box(
            modifier = Modifier
                .size(48.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                colorFilter = ColorFilter.tint(AppColors.color.onSecondary),
                contentScale = ContentScale.Fit
            )
        }
    }
}