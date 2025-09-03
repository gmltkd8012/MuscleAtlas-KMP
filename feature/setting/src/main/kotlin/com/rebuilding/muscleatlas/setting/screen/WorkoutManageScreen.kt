package com.rebuilding.muscleatlas.setting.screen

import android.text.TextUtils.isEmpty
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rebuilding.muscleatlas.design_system.theme.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseLine
import com.rebuilding.muscleatlas.design_system.base.BaseText
import com.rebuilding.muscleatlas.design_system.component.PrimaryButton
import com.rebuilding.muscleatlas.setting.unit.workoutmanage.WorkoutManageChip
import com.rebuilding.muscleatlas.setting.viewmodel.WorkoutManageViewModel

@Composable
fun WorkoutManageScreen(
    viewModel: WorkoutManageViewModel = hiltViewModel(),
    onClickEditWorkout: () -> Unit = {},
    onClickAddWorkout: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
            .padding(horizontal = 20.dp)
            .padding(WindowInsets.navigationBars.asPaddingValues())
            .padding(bottom = 12.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (state.workouts.isEmpty()) {
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
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        } else {
            val listState = rememberLazyListState()

            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {
                itemsIndexed(
                    items = state.workouts,
                    key = { _, workout -> workout.id }
                ) { index, workout ->
                    WorkoutManageChip(
                        name = workout.title,
                        onClick = {
                            onClickEditWorkout()
                        },
                    )

                    if (index != state.workouts.size - 1) {
                        BaseLine(
                            lineColor = MaterialTheme.colorScheme.secondary
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