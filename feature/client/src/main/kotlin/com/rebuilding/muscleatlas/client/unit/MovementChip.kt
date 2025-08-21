package com.rebuilding.muscleatlas.client.unit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.rebuilding.muscleatlas.design_system.base.BaseTextBox

@Composable
fun MovemenetChip(
    name: String,
    icon: ImageVector,
    movemenetList: List<String> = emptyList<String>(),
) {
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LazyRow(
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                count = movemenetList.size,
            ) { index ->
                WorkoutBox(
                    name = movemenetList[index],
                    icon = icon,
                    size = 150.dp,
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        BaseTextBox (
            modifier = Modifier
                .fillMaxSize(),
            description = "운동종목 설명"
        )
    }


}
