package com.rebuilding.muscleatlas.main.unit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rebuilding.muscleatlas.design_system.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseText
import com.rebuilding.muscleatlas.design_system.base.RoundedImage
import com.rebuilding.muscleatlas.ui.extension.clickableWithoutIndication

@Composable
internal fun SettingMenuChip(
    route: String = "",
    name: String,
    icon: ImageVector,
    onClick: (String) -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(AppColors.primary)
            .padding(16.dp)
            .clickableWithoutIndication(
                onClick = { onClick(route) }
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RoundedImage(
            icon = icon,
            size = 48.dp
        )

        Spacer(Modifier.width(12.dp))

        BaseText(
            text = name,
            style = MaterialTheme.typography.titleSmall.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            ),
            color = AppColors.onPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingMenuChipPreview() {
    SettingMenuChip(
        name = "메뉴",
        icon = Icons.Default.Settings
    )
}