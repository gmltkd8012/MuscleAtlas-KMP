package com.rebuilding.muscleatlas.setting.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rebuilding.muscleatlas.designsystem.component.ConfirmDialog
import com.rebuilding.muscleatlas.designsystem.component.ConfirmButtonStyle
import com.rebuilding.muscleatlas.designsystem.theme.AppColors
import com.rebuilding.muscleatlas.setting.viewmodel.AccountSideEffect
import com.rebuilding.muscleatlas.setting.viewmodel.AccountViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    viewModel: AccountViewModel = koinViewModel(),
    onNavigateBack: () -> Unit,
    onBackToLogin: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }
    var showDeleteErrorDialog by remember { mutableStateOf(false) }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is AccountSideEffect.NotAuthenticated -> onBackToLogin()
        }
    }

    // 에러 발생 시 다이얼로그 표시
    LaunchedEffect(state.deleteError) {
        if (state.deleteError != null) {
            showDeleteErrorDialog = true
        }
    }

    // 회원 탈퇴 확인 다이얼로그
    if (showDeleteConfirmDialog) {
        ConfirmDialog(
            title = "회원 탈퇴",
            message = "정말로 탈퇴하시겠습니까?\n\n탈퇴 시 모든 데이터가 삭제되며 복구할 수 없습니다.",
            confirmText = "탈퇴하기",
            isDestructive = true,
            onDismissRequest = { showDeleteConfirmDialog = false },
            onConfirm = {
                showDeleteConfirmDialog = false
                viewModel.deleteAccount()
            },
            onDismiss = { showDeleteConfirmDialog = false },
        )
    }

    // 에러 다이얼로그
    if (showDeleteErrorDialog && state.deleteError != null) {
        ConfirmDialog(
            title = "오류",
            message = state.deleteError ?: "알 수 없는 오류가 발생했습니다.",
            confirmButtonStyle = ConfirmButtonStyle.Text,
            onDismissRequest = {
                showDeleteErrorDialog = false
                viewModel.clearDeleteError()
            },
            onConfirm = {
                showDeleteErrorDialog = false
                viewModel.clearDeleteError()
            },
            onDismiss = null, // 취소 버튼 없음
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("계정 정보") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "뒤로가기",
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // 프로필 아이콘
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 계정 정보 카드
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                ) {
                    AccountInfoRow(
                        label = "이메일",
                        value = state.email ?: "정보 없음",
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                    AccountInfoRow(
                        label = "로그인 방식",
                        value = state.provider?.toDisplayName() ?: "정보 없음",
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                    AccountInfoRow(
                        label = "가입일",
                        value = state.createdAt ?: "정보 없음",
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 로그아웃 버튼
            Button(
                onClick = {
                    viewModel.signOut()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                ),
            ) {
                Text(
                    text = "로그아웃",
                    style = MaterialTheme.typography.bodyLarge,
                    color = AppColors.surfaceLight,
                    fontWeight = FontWeight.Medium,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 회원 탈퇴 버튼
            OutlinedButton(
                onClick = { showDeleteConfirmDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                enabled = !state.isDeleting,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error,
                ),
            ) {
                if (state.isDeleting) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.error,
                    )
                } else {
                    Text(
                        text = "회원 탈퇴",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun AccountInfoRow(
    label: String,
    value: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
        )
    }
}

private fun String.toDisplayName(): String {
    return when {
        this.contains("google") -> "Google"
        this.contains("kakao") -> "카카오"
        this.contains("apple") -> "Apple"
        else -> this
    }
}
