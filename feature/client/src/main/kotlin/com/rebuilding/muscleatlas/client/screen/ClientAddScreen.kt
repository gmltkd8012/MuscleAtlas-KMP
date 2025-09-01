package com.rebuilding.muscleatlas.client.screen

import android.R.attr.name
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rebuilding.muscleatlas.design_system.theme.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseText
import com.rebuilding.muscleatlas.design_system.component.BaseTextField
import com.rebuilding.muscleatlas.design_system.component.BaseTopBar
import com.rebuilding.muscleatlas.design_system.component.PrimaryButton
import com.rebuilding.muscleatlas.model.Client
import com.rebuilding.muscleatlas.model.ClientBottomSheetData
import java.util.UUID

@Composable
fun ClientBottomSheetScreen(
    client: ClientBottomSheetData,
    onSave: (Client) -> Unit,
    onDismissRequest: () -> Unit,
) {
    Log.e("heesang", "[ClientAddBottomSheet] id: ${client.id} | name: ${client.name} | memo: ${client.memo} ")

    var nameTextFiled by remember { mutableStateOf(TextFieldState(client.name ?: "")) }
    var memoTextFiled by remember { mutableStateOf(TextFieldState(client.memo ?: "")) }

    Scaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.primary),
        topBar = {
            BaseTopBar(
                title = {
                    BaseText(
                        text = "회원 추가",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                },
            )
        },
        bottomBar = {
            PrimaryButton(
                text = "회원 추가",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 25.dp),
                onClick = {
                    onSave(
                        Client(
                            id = client.id ?: UUID.randomUUID().toString(),
                            name = nameTextFiled.text.toString(),
                            memo = memoTextFiled.text.toString(),
                        )
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(25.dp))

            BaseTextField(
                textFieldValue = nameTextFiled.text.toString(),
                onTextChanged = { text ->
                    nameTextFiled = TextFieldState(text)
                },
                hint = "이름",
                startInputLocation = Alignment.CenterVertically,
            )

            Spacer(Modifier.height(16.dp))

            BaseTextField(
                textFieldValue = memoTextFiled.text.toString(),
                onTextChanged = { text ->
                    memoTextFiled = TextFieldState(text)
                },
                hint = "메모",
                startInputLocation = Alignment.CenterVertically,
            )

            Spacer(Modifier.height(100.dp))
        }
    }
}