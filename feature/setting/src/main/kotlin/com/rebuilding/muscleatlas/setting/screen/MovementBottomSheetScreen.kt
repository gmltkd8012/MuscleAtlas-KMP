package com.rebuilding.muscleatlas.setting.screen

import android.R.attr.text
import android.R.attr.type
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rebuilding.muscleatlas.design_system.theme.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseText
import com.rebuilding.muscleatlas.design_system.base.SquaredImageBox
import com.rebuilding.muscleatlas.design_system.component.BaseTextField
import com.rebuilding.muscleatlas.design_system.component.BaseTopBar
import com.rebuilding.muscleatlas.design_system.component.PrimaryButton
import com.rebuilding.muscleatlas.model.MovementData
import com.rebuilding.muscleatlas.model.MovmentBottomSheetData
import com.rebuilding.muscleatlas.ui.extension.MovementBottomSheetState
import java.util.UUID


@Composable
fun MovementBottomSheetScreen(
    state: MovementBottomSheetState,
    onSaveMovement: (MovementData) -> Unit,
) {
    var titleTextFiled by remember { mutableStateOf(TextFieldState(state.value.title ?: "")) }
    var descriptionTextFiled by remember { mutableStateOf(TextFieldState(state.value.description ?: "")) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        BaseTopBar(
            title = {
                BaseText(
                    text = "세부 동작 추가",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .imePadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(25.dp))

                SquaredImageBox(
                    modifier = Modifier.size(200.dp),
                    icon = null,
                    onClick = { }
                )

                Spacer(Modifier.height(25.dp))

                BaseTextField(
                    textFieldValue = titleTextFiled.text.toString(),
                    onTextChanged = { text ->
                        titleTextFiled = TextFieldState(text)
                    },
                    hint = "세부 동작 제목을 적어주세요.",
                    startInputLocation = Alignment.CenterVertically,
                )

                Spacer(Modifier.height(16.dp))

                BaseTextField(
                    textFieldValue = descriptionTextFiled.text.toString(),
                    onTextChanged = { text ->
                        descriptionTextFiled = TextFieldState(text)
                    },
                    hint = "세부 동작에 대한 설명을 적어주세요.",
                    startInputLocation = Alignment.Top,
                    modifier = Modifier.height(200.dp)  // weight 대신 고정 높이
                )

                Spacer(Modifier.height(32.dp))
            }
        }

        PrimaryButton(
            text = "동작 추가",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 25.dp),
            onClick = {
                if (titleTextFiled.text.length > 0 && descriptionTextFiled.text.length > 0) {
                    onSaveMovement(
                        MovementData(
                            id = UUID.randomUUID().toString(),
                            workoutId = state.value.workoutId ?: "ERROR",
                            contraction = state.value.contraction ?: -1,
                            type = state.value.type ?: -1,
                            imgUrl = null,
                            title = titleTextFiled.text.toString(),
                            description = descriptionTextFiled.text.toString(),
                            currentMills = System.currentTimeMillis(),
                        )
                    )
                }
            }
        )
    }
}