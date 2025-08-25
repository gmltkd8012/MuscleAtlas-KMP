package com.rebuilding.muscleatlas.setting.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontVariation.weight
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rebuilding.muscleatlas.design_system.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseText
import com.rebuilding.muscleatlas.design_system.base.SquaredImageBox
import com.rebuilding.muscleatlas.design_system.component.BaseTextField
import com.rebuilding.muscleatlas.design_system.component.BaseTopBar
import com.rebuilding.muscleatlas.design_system.component.PrimaryButton

@Composable
fun MovementBottomSheetScreen(
    onDismissRequest: () -> Unit,
) {

    var titleTextFiled by remember { mutableStateOf(TextFieldState("")) }
    var descriptionTextFiled by remember { mutableStateOf(TextFieldState("")) }

    Scaffold(
        modifier = Modifier.background(AppColors.primary),
        topBar = {
            BaseTopBar(
                title = {
                    BaseText(
                        text = "세부 동작 추가",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = AppColors.onPrimary,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                },
            )
        },
        bottomBar = {
            PrimaryButton(
                text = "동작 추가",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 25.dp),
                onClick = {
                    onDismissRequest()
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.primary)
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(25.dp))

            SquaredImageBox(
                modifier = Modifier.size(200.dp),
                icon = null,
                onClick = {

                }
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
                modifier = Modifier.weight(1f)
            )

            Spacer(Modifier.height(100.dp))
        }
    }
}