package com.rebuilding.muscleatlas.workout.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.rebuilding.muscleatlas.workout.component.WorkoutListItem
import com.rebuilding.muscleatlas.workout.viewmodel.WorkoutViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WorkoutScreen(
    viewModel: WorkoutViewModel = koinViewModel(),
    onNavigateToDetail: () -> Unit,
) {
    val testWorkouts = remember {
        (1..20).map { "운동 $it" }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {

        //TODO - 클릭 시, 데이터 연동 필요 (profile id 등..)
        items(testWorkouts) { workout ->
            WorkoutListItem(
                title = workout,
                onClick = onNavigateToDetail,
            )
        }
    }
}
