package com.rebuilding.muscleatlas.workout.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rebuilding.muscleatlas.designsystem.component.BaseTextField
import com.rebuilding.muscleatlas.workout.component.WorkoutListItem
import com.rebuilding.muscleatlas.workout.viewmodel.WorkoutSideEffect
import com.rebuilding.muscleatlas.workout.viewmodel.WorkoutViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutScreen(
    viewModel: WorkoutViewModel = koinViewModel(),
    onNavigateToDetail: (exerciseId: String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val colorScheme = MaterialTheme.colorScheme
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    
    // SideEffect 처리
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is WorkoutSideEffect.ShowAddExerciseSheet -> {
                showBottomSheet = true
            }
            is WorkoutSideEffect.HideAddExerciseSheet -> {
                showBottomSheet = false
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background),
    ) {
        when {
            state.isLoading && state.exercises.isEmpty() -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = colorScheme.primary,
                )
            }
            state.exercises.isEmpty() -> {
                Text(
                    text = "등록된 운동이 없습니다\n운동을 추가해보세요!",
                    modifier = Modifier.align(Alignment.Center),
                    color = colorScheme.onSurfaceVariant,
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(
                        items = state.exercises,
                        key = { it.id },
                    ) { exercise ->
                        WorkoutListItem(
                            title = exercise.name,
                            imgUrl = exercise.exerciseImg,
                            onClick = { onNavigateToDetail(exercise.id) },
                        )
                    }
                }
            }
        }
        
        // 운동 추가 FAB
        FloatingActionButton(
            onClick = { viewModel.onAddExerciseClick() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = colorScheme.primary,
            contentColor = colorScheme.onPrimary,
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "운동 추가",
            )
        }
    }
    
    // 운동 추가 BottomSheet
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            containerColor = colorScheme.surface,
            modifier = Modifier.fillMaxWidth(),
        ) {
            AddExerciseSheetContent(
                onAddClick = { name ->
                    viewModel.addExercise(name)
                },
            )
        }
    }
}

@Composable
private fun AddExerciseSheetContent(
    onAddClick: (name: String) -> Unit,
) {
    var name by remember { mutableStateOf("") }
    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorScheme.surface)
            .padding(horizontal = 16.dp)
            .padding(bottom = 32.dp),
    ) {
        Text(
            text = "운동 추가",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp),
        )

        BaseTextField(
            value = name,
            labelText = "웨이트 종목",
            hintText = "종목을 입력하세요",
            onValueChanged = { name = it },
            onDelete = { name = "" }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onAddClick(name) },
            modifier = Modifier.fillMaxWidth(),
            enabled = name.isNotBlank(),
            shape = RoundedCornerShape(12.dp),
        ) {
            Text("추가")
        }
    }
}
