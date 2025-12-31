package com.rebuilding.muscleatlas.member.screen

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rebuilding.muscleatlas.member.component.MemberListItem
import com.rebuilding.muscleatlas.member.viewmodel.MemberSideEffect
import com.rebuilding.muscleatlas.member.viewmodel.MemberViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemberScreen(
    viewModel: MemberViewModel = koinViewModel(),
    onNavigateToDetail: () -> Unit,
) {
    val testMembers = remember {
        (1..20).map { "회원 $it" }
    }

    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    // SideEffect 처리
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is MemberSideEffect.ShowAddMemberSheet -> {
                showBottomSheet = true
            }

            is MemberSideEffect.HideAddMemberSheet -> {
                showBottomSheet = false
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            //TODO - 실제 데이터 연동 해야함
            items(testMembers) { member ->
                MemberListItem(
                    title = member,
                    onClick = onNavigateToDetail,
                )
            }
        }

        // 회원 추가 FAB
        FloatingActionButton(
            onClick = { viewModel.onAddMemberClick() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = Color(0xFF2196F3),
            contentColor = Color.White,
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "회원 추가",
            )
        }
    }

    // 회원 추가 BottomSheet
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
        ) {
            AddMemberSheetContent(
                onAddClick = { name, memo ->
                    viewModel.onAddMember(name, memo)
                },
            )
        }
    }
}

@Composable
private fun AddMemberSheetContent(
    onAddClick: (name: String, memo: String) -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var memo by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 32.dp),
    ) {
        Text(
            text = "회원 추가",
            modifier = Modifier.padding(bottom = 16.dp),
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("회원 이름") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = memo,
            onValueChange = { memo = it },
            label = { Text("간략한 메모") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 2,
            maxLines = 4,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onAddClick(name, memo) },
            modifier = Modifier.fillMaxWidth(),
            enabled = name.isNotBlank(),
        ) {
            Text("추가")
        }
    }
}
