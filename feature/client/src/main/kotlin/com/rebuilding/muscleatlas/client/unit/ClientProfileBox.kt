package com.rebuilding.muscleatlas.client.unit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rebuilding.muscleatlas.design_system.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseText
import com.rebuilding.muscleatlas.design_system.component.ProfileChip

@Composable
fun ClientProfileBox(
    name: String,
    memo: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(AppColors.secondary)
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileChip(
            name = name,
            size = 108.dp,
        )

        Spacer(Modifier.height(12.dp))

        BaseText(
            text = name,
            style = MaterialTheme.typography.titleSmall.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            ),
            color = AppColors.onPrimary
        )

        Spacer(Modifier.height(6.dp))

        BaseText(
            text = memo,
            style = MaterialTheme.typography.titleSmall.copy(
                fontSize = 16.sp,
            ),
            color = AppColors.onSecondary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ClientProfileBoxPreview() {
    ClientProfileBox(
        name = "테스터",
        memo = "테스트"
    )
}