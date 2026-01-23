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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rebuilding.muscleatlas.designsystem.component.BaseTextField
import com.rebuilding.muscleatlas.member.component.MemberListItem
import com.rebuilding.muscleatlas.member.viewmodel.MemberSideEffect
import com.rebuilding.muscleatlas.member.viewmodel.MemberViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemberScreen(
    viewModel: MemberViewModel = koinViewModel(),
    onNavigateToDetail: (memberId: String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val colorScheme = MaterialTheme.colorScheme
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isNavigating by remember { mutableStateOf(false) }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        isNavigating = false
        viewModel.loadMembers()
    }

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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("회원") },
                actions = {
                    TextButton(
                        onClick = { viewModel.onAddMemberClick() },
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
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.isLoading && state.members.isEmpty() -> {
                    // 초기 로딩
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = colorScheme.primary,
                    )
                }
                state.members.isEmpty() -> {
                    // 빈 상태
                    Text(
                        text = "등록된 회원이 없습니다\n회원을 추가해보세요!",
                        modifier = Modifier.align(Alignment.Center),
                        color = colorScheme.onSurfaceVariant,
                    )
                }

                else -> {
                    // 회원 목록
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        items(
                            items = state.members,
                            key = { it.id }
                        ) { member ->
                            MemberListItem(
                                title = member.name,
                                onClick = {
                                    if (!isNavigating) {
                                        isNavigating = true
                                        onNavigateToDetail(member.id)
                                    }
                                },
                            )
                        }
                    }
                }
            }
        }
    }

    // 회원 추가 BottomSheet
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            containerColor = colorScheme.surface,
            modifier = Modifier.fillMaxWidth(),
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
    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorScheme.surface)
            .padding(horizontal = 16.dp)
            .padding(bottom = 32.dp),
    ) {
        Text(
            text = "회원 추가",
            color = colorScheme.onSurface,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 16.dp),
        )

        BaseTextField(
            value = name,
            labelText = "회원 이름",
            hintText = "이름을 입력하세요",
            onValueChanged = { name = it },
            onDelete = { name = "" }
        )

        Spacer(modifier = Modifier.height(12.dp))

        BaseTextField(
            value = memo,
            labelText = "메모",
            hintText = "메모를 입력하세요",
            singleLine = false,
            onValueChanged = { memo = it },
            onDelete = { memo = "" }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onAddClick(name, memo) },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            enabled = name.isNotBlank(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.primary,
                contentColor = colorScheme.onPrimary,
            ),
        ) {
            Text("추가")
        }
    }
}
