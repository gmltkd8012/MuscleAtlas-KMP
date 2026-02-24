package com.rebuilding.muscleatlas.group.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rebuilding.muscleatlas.designsystem.component.BaseTextField
import com.rebuilding.muscleatlas.group.component.GroupListItem
import com.rebuilding.muscleatlas.group.component.WorkoutInGroupItem
import com.rebuilding.muscleatlas.group.viewmodel.GroupSideEffect
import com.rebuilding.muscleatlas.group.viewmodel.GroupViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupScreen(
    viewModel: GroupViewModel = koinViewModel(),
    onNavigateBack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val colorScheme = MaterialTheme.colorScheme

    var showGroupAddBottomSheet by remember { mutableStateOf(false) }
    val groupAddSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var showExerciseSelectBottomSheet by remember { mutableStateOf(false) }
    val exerciseSelectSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is GroupSideEffect.ShowAddGroupSheet -> {
                showGroupAddBottomSheet = true
            }
            is GroupSideEffect.HideAddGroupSheet -> {
                showGroupAddBottomSheet = false
            }
            is GroupSideEffect.ShowExerciseSelectSheet -> {
                showExerciseSelectBottomSheet = true
            }
            is GroupSideEffect.HideExerciseSelectSheet -> {
                showExerciseSelectBottomSheet = false
            }
        }
    }

    Scaffold(
        containerColor = colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "대분류 관리",
                        color = colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "뒤로가기",
                            tint = colorScheme.onBackground,
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = { viewModel.onAddClick() },
                        enabled = true,
                    ) {
                        Text(
                            text = "추가",
                            color = colorScheme.primary,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorScheme.background,
                ),
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(colorScheme.background),
        ) {

            when {
                state.isLoading && state.exerciseGroups.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = colorScheme.primary,
                        )
                    }
                }
                state.exerciseGroups.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "운동 종목을 분류하여 한눈에 확인하세요.",
                            color = colorScheme.onSurfaceVariant,
                        )
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        items(
                            items = state.exerciseGroups,
                            key = { it.id },
                        ) { group ->
                            GroupListItem(
                                title = group.name,
                                onClick = { viewModel.onSelectExercisesClick(group.id) },
                                exerciseCount = state.groupExerciseCounts[group.id] ?: 0
                            )
                        }
                    }
                }
            }

        }
    }

    // 그룹 추가 BottomSheet
    if (showGroupAddBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showGroupAddBottomSheet = false },
            sheetState = groupAddSheetState,
            containerColor = colorScheme.surface,
            modifier = Modifier.fillMaxWidth(),
        ) {
            AddGroupSheetContent(
                onAddClick = { name ->
                    viewModel.addExerciseGroup(name)
                },
            )
        }
    }

    // 운동 선택 BottomSheet
    if (showExerciseSelectBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showExerciseSelectBottomSheet = false },
            sheetState = exerciseSelectSheetState,
            containerColor = colorScheme.surface,
            modifier = Modifier.fillMaxWidth(),
        ) {
            ExerciseSelectSheetContent(
                exercises = state.exercises,
                selectedExerciseIds = state.selectedExerciseIds,
                onExerciseToggle = { exerciseId ->
                    viewModel.toggleExerciseSelection(exerciseId)
                },
                onSaveClick = {
                    viewModel.saveSelectedExercises()
                },
                onDeleteClick = {
                    viewModel.deleteExerciseGroup()
                },
            )
        }
    }
}

@Composable
private fun ExerciseSelectSheetContent(
    exercises: List<com.rebuilding.muscleatlas.data.model.Exercise>,
    selectedExerciseIds: Set<String>,
    onExerciseToggle: (String) -> Unit,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorScheme.surface)
            .padding(horizontal = 16.dp)
            .padding(bottom = 32.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "운동 선택",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f),
            )

            TextButton(onClick = onDeleteClick) {
                Text(
                    text = "삭제",
                    color = colorScheme.error,
                )
            }
        }

        // 운동 목록
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = false)
                .height(400.dp),
        ) {
            items(
                items = exercises,
                key = { it.id }
            ) { exercise ->
                WorkoutInGroupItem(
                    title = exercise.name,
                    imgUrl = exercise.exerciseImg,
                    isChecked = selectedExerciseIds.contains(exercise.id),
                    onCheckedChange = { onExerciseToggle(exercise.id) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 저장 버튼
        Button(
            onClick = onSaveClick,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            enabled = selectedExerciseIds.isNotEmpty(),
            shape = RoundedCornerShape(12.dp),
        ) {
            Text("저장 (${selectedExerciseIds.size})")
        }
    }
}

@Composable
private fun AddGroupSheetContent(
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
            text = "그룹 추가",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp),
        )

        BaseTextField(
            value = name,
            labelText = "그룹명",
            hintText = "그룹명을 입력하세요",
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
