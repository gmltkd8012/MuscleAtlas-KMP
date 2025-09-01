package com.rebuilding.muscleatlas.main.unit

import android.R.attr.name
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
import com.rebuilding.muscleatlas.design_system.theme.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseText
import com.rebuilding.muscleatlas.design_system.component.ProfileChip
import com.rebuilding.muscleatlas.design_system.component.SwipeItemChip
import com.rebuilding.muscleatlas.model.Client
import com.rebuilding.muscleatlas.ui.extension.clickableWithoutIndication

@Composable
internal fun ClientInfoChip(
    client: Client,
    profileImg: String? = null,
    swipedItemId: String? = null,
    onClick: () -> Unit = {},
    onDelete: (String) -> Unit = {},
    onEdit: (Client) -> Unit = {},
    onSwipe: (String) -> Unit = {},
) {
    SwipeItemChip(
        itemId = client.id,
        swipedItemId = swipedItemId,
        onSwipe = onSwipe,
        onDelete = { onDelete(client.id) },
        onEdit = { onEdit(client) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp)
                .clickableWithoutIndication(
                    onClick = onClick
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ProfileChip(
                name = client.name,
                size = 48.dp,
                profileImg = profileImg
            )

            Spacer(Modifier.width(12.dp))

            Column{
                BaseText(
                    text = client.name,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onPrimary
                )

                Spacer(Modifier.height(6.dp))

                BaseText(
                    text = client.memo,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 12.sp,
                    ),
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ClientInfoChipPreview() {
    ClientInfoChip(
        client = Client(
            id = "",
            name = "테스트",
            memo = "평화체육관 고객",
        ),
        profileImg = null
    )
}