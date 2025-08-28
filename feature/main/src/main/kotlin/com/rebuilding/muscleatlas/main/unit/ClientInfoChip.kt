package com.rebuilding.muscleatlas.main.unit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rebuilding.muscleatlas.design_system.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseText
import com.rebuilding.muscleatlas.design_system.component.ProfileChip
import com.rebuilding.muscleatlas.design_system.component.SwipeItemChip
import com.rebuilding.muscleatlas.ui.extension.clickableWithoutIndication

@Composable
internal fun ClientInfoChip(
    name: String,
    memo: String,
    profileImg: String? = null,
    onClick: () -> Unit = {},
    onDelete: () -> Unit = {},
) {
    SwipeItemChip(
        onDelete = onDelete
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(AppColors.color.primary)
                .padding(16.dp)
                .clickableWithoutIndication(
                    onClick = onClick
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ProfileChip(
                name = name,
                size = 48.dp,
                profileImg = profileImg
            )

            Spacer(Modifier.width(12.dp))

            Column{
                BaseText(
                    text = name,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = AppColors.color.onPrimary
                )

                Spacer(Modifier.height(6.dp))

                BaseText(
                    text = memo,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 12.sp,
                    ),
                    color = AppColors.color.onSecondary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClientInfoChipPreview() {
    ClientInfoChip(
        name = "테스트",
        memo = "평화체육관 고객",
        profileImg = null
    )
}