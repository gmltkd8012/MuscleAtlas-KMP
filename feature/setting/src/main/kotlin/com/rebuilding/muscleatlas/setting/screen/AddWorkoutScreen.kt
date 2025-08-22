package com.rebuilding.muscleatlas.setting.screen

import android.R.attr.text
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rebuilding.muscleatlas.design_system.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseLine
import com.rebuilding.muscleatlas.design_system.base.BaseText
import com.rebuilding.muscleatlas.design_system.component.BaseTextField
import com.rebuilding.muscleatlas.design_system.component.PrimaryButton
import com.rebuilding.muscleatlas.setting.unit.addworkout.RegistedMovementChip
import com.rebuilding.muscleatlas.setting.unit.workoutmanage.WorkoutManageChip

@Composable
fun AddWorkoutScreen(
    onClickBack: () -> Unit = {},
    onClickSave: () -> Unit = {},
) {
    var textNameFieldValue by remember { mutableStateOf<TextFieldState>(TextFieldState("")) }
    var textDescriptionFieldValue by remember { mutableStateOf<TextFieldState>(TextFieldState("")) }

    BackHandler {
        onClickBack()
    }

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppColors.primary)
            .padding(horizontal = 20.dp)
            .padding(WindowInsets.navigationBars.asPaddingValues())
            .padding(bottom = 12.dp)
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
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
                listOf(
                    "자세 1",
                    "자세 2",
                    "자세 3",
                    "자세 4",
                    "자세 5",
                    "자세 6",
                    "자세 7",
                    "자세 8",
                )
            )

            BaseText(
                text = "설명",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = AppColors.onPrimary,
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

        PrimaryButton(
            text = "운동 추가",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            onClick = onClickSave
        )
    }
}