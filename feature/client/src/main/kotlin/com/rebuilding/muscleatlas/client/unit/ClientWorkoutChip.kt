package com.rebuilding.muscleatlas.client.unit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rebuilding.muscleatlas.design_system.theme.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseText
import com.rebuilding.muscleatlas.model.WorkoutData

@Composable
fun ClientWorkoutChip(
    workoutList: List<WorkoutData> = emptyList<WorkoutData>(),
    onClickedWorkout: (WorkoutData) -> Unit = {},
) {
    if (workoutList.isEmpty()) { // TODO - 등록된 운동 항목이 없는 경우
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            BaseText(
                text = "등록된 운동이 존재하지 않습니다.",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight(500)
                ),
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            BaseText(
                text = "Workouts",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 100.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(workoutList.size) { index ->
                    WorkoutBox(
                        name = workoutList[index].title,
                        icon = Icons.Default.Warning,
                        onClick = {
                            onClickedWorkout(workoutList[index])
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ClientWorkoutChipPreview() {
    ClientWorkoutChip(
        workoutList = listOf(
            WorkoutData(title = "벤치프레스"),
            WorkoutData(title = "스쿼트"),
            WorkoutData(title = "데드리프트"),
        )
    )
}

@Preview
@Composable
private fun ClientWorkoutChipEmptyPreview() {
    ClientWorkoutChip()
}