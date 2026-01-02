package com.rebuilding.muscleatlas.workout.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rebuilding.muscleatlas.workout.component.WorkoutListItem
import com.rebuilding.muscleatlas.workout.viewmodel.WorkoutViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WorkoutScreen(
    viewModel: WorkoutViewModel = koinViewModel(),
    onNavigateToDetail: (exerciseId: String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val colorScheme = MaterialTheme.colorScheme

    when {
        state.isLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorScheme.background),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(color = colorScheme.primary)
            }
        }

        state.error != null -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorScheme.background)
                    .padding(16.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "오류: ${state.error}",
                    color = colorScheme.onBackground,
                )
            }
        }

        state.exercises.isEmpty() -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorScheme.background),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "등록된 운동이 없습니다",
                    color = colorScheme.onSurfaceVariant,
                )
            }
        }

        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorScheme.background),
            ) {
                items(
                    items = state.exercises,
                    key = { it.id },
                ) { exercise ->
                    WorkoutListItem(
                        title = exercise.name,
                        onClick = { onNavigateToDetail(exercise.id) },
                    )
                }
            }
        }
    }
}
