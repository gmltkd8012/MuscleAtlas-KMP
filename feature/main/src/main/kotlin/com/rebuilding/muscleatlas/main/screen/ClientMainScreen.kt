package com.rebuilding.muscleatlas.main.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rebuilding.muscleatlas.main.unit.ClientInfoChip
import com.rebuilding.muscleatlas.design_system.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseLine

@Composable
fun ClientMainScreen(
    onClickProfile: () -> Unit = {},
) {
    val listState = rememberLazyListState()
    val testSize = 20 //TODO - 데이터 연동 시, 삭제 예정

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.color.primary)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState
    ) {
        items(
            count = testSize,
        ) { index ->
            ClientInfoChip(
                name = "테스트 회원 $index",
                memo = "체육관 $index",
                onClick = onClickProfile,
            )

            if (index != testSize - 1) {
                BaseLine(
                    lineColor = AppColors.color.secondary
                )
            }
        }
    }
}