package com.rebuilding.muscleatlas.setting.screen

import android.R.attr.bottom
import android.R.attr.text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rebuilding.muscleatlas.design_system.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseLine
import com.rebuilding.muscleatlas.design_system.base.BaseText
import com.rebuilding.muscleatlas.design_system.component.PrimaryButton
import com.rebuilding.muscleatlas.setting.unit.workoutmanage.WorkoutManageChip

@Composable
fun WorkoutManageScreen(
    onClickEditWorkout: () -> Unit = {},
    onClickAddWorkout: () -> Unit = {},
) {
    val test = listOf<String>("운동 1", "운동 2", "운동 3")

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppColors.color.primary)
            .padding(horizontal = 20.dp)
            .padding(WindowInsets.navigationBars.asPaddingValues())
            .padding(bottom = 12.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (test.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxWidth().weight(1f),
                contentAlignment = Alignment.Center
            ) {
                BaseText(
                    text = "운동을 등록해 주세요.",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight(500)
                    ),
                    color = AppColors.color.onSecondary
                )
            }
        } else {
            val listState = rememberLazyListState()

            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {
                items(
                    count = test.size
                ) { index ->
                    WorkoutManageChip(
                        name = test[index],
                        onClick = {
                            onClickEditWorkout()
                        },
                    )

                    if (index != test.size - 1) {
                        BaseLine(
                            lineColor = AppColors.color.secondary
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        PrimaryButton(
            text = "운동 추가",
            modifier = Modifier.fillMaxWidth(),
            onClick = onClickAddWorkout
        )
    }
}