package com.rebuilding.muscleatlas.client.screen

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.unit.dp
import com.rebuilding.muscleatlas.client.component.ClientProfileTopBar
import com.rebuilding.muscleatlas.client.unit.ClientProfileBox
import com.rebuilding.muscleatlas.client.unit.ClientWorkoutChip
import com.rebuilding.muscleatlas.client.unit.MovemenetChip
import com.rebuilding.muscleatlas.design_system.AppColors

@Composable
fun ClientProfileScreen(
    intent: Intent,
    onClickBack: () -> Unit = {},
) {
    var isProfileScreen by remember { mutableStateOf(true) }

    Scaffold(
        modifier = Modifier.background(AppColors.primary),
        topBar = {
            ClientProfileTopBar(
                title = if (isProfileScreen) "회원 정보" else "운동 정보",
                onClickBack = {
                    if (isProfileScreen) onClickBack()
                    else isProfileScreen = true

                }
            )
        },
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.primary)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ClientProfileBox(
                clientName = "테스터",
                memo = "테스트 체육관",
                workoutName = "벤치 프레스",
                isColumn = isProfileScreen
            )

            if (isProfileScreen) {
                Spacer(Modifier.height(16.dp))

                ClientWorkoutChip(
                    workoutList = listOf(
                        "벤치프레스",
                        "스쿼트",
                        "데드리프트",
                        "밀리터리프레스",
                        "인클라인벤치프레스",
                        "덤벨숄더프레스",
                        "런지",
                        "레그컬",
                        "트라이셉스익스텐션",
                        "바벨컬",
                    ),
                    onClickedWorkout = {
                        isProfileScreen = false
                    }
                )
            } else {
                Spacer(Modifier.height(32.dp))

                MovemenetChip(
                    name = "구분 동작",
                    icon = Icons.Default.Warning,
                    movemenetList = listOf(
                        "동작 1",
                        "동작 2",
                        "동작 3",
                        "동작 4",
                        "동작 5",
                        "동작 6",
                        "동작 7",
                    )
                )
            }
        }

    }
}