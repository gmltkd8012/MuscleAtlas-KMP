package com.rebuilding.muscleatlas.client.screen

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.unit.dp
import com.rebuilding.muscleatlas.client.component.ClientProfileTopBar
import com.rebuilding.muscleatlas.client.unit.ClientProfileBox
import com.rebuilding.muscleatlas.client.unit.ClientWorkoutChip
import com.rebuilding.muscleatlas.design_system.AppColors

@Composable
fun ClientProfileScreen(
    intent: Intent,
    onClickBack: () -> Unit = {},
) {
    Scaffold(
        modifier = Modifier.background(AppColors.primary),
        topBar = {
            ClientProfileTopBar(
                title = "회원 정보",
                onClickBack = onClickBack
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
                name = "테스터",
                memo = "테스트 체육관"
            )

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
                )
            )


        }

    }
}