package com.rebuilding.muscleatlas.client.unit

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rebuilding.muscleatlas.design_system.theme.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseText
import com.rebuilding.muscleatlas.model.MovementData

@Composable
fun MovemenetChip(
    title: String,
    icon: ImageVector,
    movemenetList: List<MovementData> = emptyList<MovementData>(),
    onClick: () -> Unit = {},
) {
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        BaseText(
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 4.dp)
        )

        Spacer(Modifier.height(8.dp))

        if (movemenetList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                BaseText(
                    text = "등록된 세부 동작이 없습니다.",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Start,
                )
            }
        } else {
            LazyRow(
                state = listState,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(
                    count = movemenetList.size,
                ) { index ->
                    WorkoutBox(
                        name = movemenetList[index].title,
                        icon = icon,
                        size = 150.dp,
                        onClick = onClick
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MovementChipPreview() {
    MovemenetChip(
        title = "Movement",
        icon = Icons.Default.Warning,
        movemenetList = listOf(
            MovementData(
                "세부 동작 1"
            ),
            MovementData(
                "세부 동작 2"
            ),
            MovementData(
                "세부 동작 3"
            )
        )
    )
}

@Preview
@Composable
private fun MovementChipEmptyPreview() {
    MovemenetChip(
        title = "Movement",
        icon = Icons.Default.Warning,
    )
}