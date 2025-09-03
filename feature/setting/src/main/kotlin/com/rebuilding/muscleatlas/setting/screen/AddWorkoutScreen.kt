package com.rebuilding.muscleatlas.setting.screen

import android.R.attr.type
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rebuilding.muscleatlas.design_system.theme.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseText
import com.rebuilding.muscleatlas.design_system.component.BaseBottomSheet
import com.rebuilding.muscleatlas.design_system.component.BaseTextField
import com.rebuilding.muscleatlas.design_system.component.PrimaryButton
import com.rebuilding.muscleatlas.model.MovementData
import com.rebuilding.muscleatlas.setting.unit.addworkout.RegistedMovementChip
import com.rebuilding.muscleatlas.setting.viewmodel.WorkoutAddSdieEffect
import com.rebuilding.muscleatlas.setting.viewmodel.WorkoutAddViewModel
import com.rebuilding.muscleatlas.ui.extension.hide
import com.rebuilding.muscleatlas.ui.extension.isShown
import com.rebuilding.muscleatlas.ui.extension.rememberMovementBottomSheetState
import com.rebuilding.muscleatlas.ui.extension.show

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWorkoutScreen(
    viewModel: WorkoutAddViewModel = hiltViewModel(),
    onClickBack: () -> Unit = {},
    onClickSave: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()

    var textNameFieldValue by remember { mutableStateOf<TextFieldState>(TextFieldState("")) }
    var textDescriptionFieldValue by remember { mutableStateOf<TextFieldState>(TextFieldState("")) }

    val movementAddBottomSheetState = rememberMovementBottomSheetState<WorkoutAddSdieEffect>()

    BackHandler {
        onClickBack()
    }

    LaunchedEffect(Unit) {
        viewModel.getMovement(null)
    }

    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect) {
            is WorkoutAddSdieEffect.ShowMovementAddBottomSheet -> {
                movementAddBottomSheetState.show(
                    movement = sideEffect.movement
                )
            }
        }
    }

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
            .padding(WindowInsets.navigationBars.asPaddingValues())
            .padding(bottom = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            BaseTextField(
                textFieldValue = textNameFieldValue.text.toString(),
                onTextChanged = { text ->
                    textNameFieldValue = TextFieldState(text)
                },
                hint = "운동 이름을 입력해주세요.",
                startInputLocation = Alignment.CenterVertically,
            )

            RegistedMovementChip(
                joinMovementList = state.joinMovementList,
                stabilizationMechanismList = state.stabilizationMechanismList,
                muscularRelationList = state.neuromuscularRelationList,
                onClickEdit = { movement ->
                    viewModel.showMovementBottomSheet(movement)
                },
                onClickAdd = { type ->
                    viewModel.showMovementBottomSheet(
                        MovementData(
                            id = "",
                            workoutId = "test_id_0001",
                            type = type,
                            imgUrl = null,
                            title = "",
                            description = "",
                            currentMills = 0L,
                        )
                    )
                }
            )

            BaseText(
                text = "설명",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Start
            )

            Spacer(Modifier.height(4.dp))

            BaseTextField(
                textFieldValue = textDescriptionFieldValue.text.toString(),
                onTextChanged = { text ->
                    textDescriptionFieldValue = TextFieldState(text)
                },
                hint = "운동 설명을 입력해주세요.",
                startInputLocation = Alignment.Top,
                modifier = Modifier.height(400.dp)
            )

            Spacer(Modifier.fillMaxWidth().height(100.dp))
        }

        Box (
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                .align(Alignment.BottomCenter),
            contentAlignment = Alignment.Center
        ) {
            PrimaryButton(
                text = "저장",
                modifier = Modifier.fillMaxWidth(),
                onClick = onClickSave
            )
        }
    }

    if (movementAddBottomSheetState.isShown) {
        BaseBottomSheet(
            modifier = Modifier.fillMaxWidth(),
            sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true,
                confirmValueChange = { state ->
                    state != SheetValue.PartiallyExpanded
                },
            ),
            onDismissRequest = {
                movementAddBottomSheetState.hide()
            },
        ) {
            MovementBottomSheetScreen(
                state = movementAddBottomSheetState,
                onSaveMovement = { movement ->
                    viewModel.updateMovementUI(movement)
                    movementAddBottomSheetState.hide()
                }
            )
        }
    }
}