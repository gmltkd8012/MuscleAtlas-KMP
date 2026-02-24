package com.rebuilding.muscleatlas.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * 확인/취소 다이얼로그 컴포넌트
 *
 * @param title 다이얼로그 제목
 * @param message 다이얼로그 메시지
 * @param confirmText 확인 버튼 텍스트 (기본: "확인")
 * @param dismissText 취소 버튼 텍스트 (기본: "취소")
 * @param isDestructive 위험한 작업 여부 (true일 경우 확인 버튼이 error 색상)
 * @param confirmButtonStyle 확인 버튼 스타일 (Filled, Outlined, Text)
 * @param onDismissRequest 다이얼로그 닫기 콜백
 * @param onConfirm 확인 버튼 클릭 콜백
 * @param onDismiss 취소 버튼 클릭 콜백 (null일 경우 취소 버튼 미표시)
 */
@Composable
fun ConfirmDialog(
    title: String,
    message: String,
    confirmText: String = "확인",
    dismissText: String = "취소",
    isDestructive: Boolean = false,
    confirmButtonStyle: ConfirmButtonStyle = ConfirmButtonStyle.Filled,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    onDismiss: (() -> Unit)? = null,
) {
    val colorScheme = MaterialTheme.colorScheme

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
            )
        },
        text = {
            Text(text = message)
        },
        shape = RoundedCornerShape(12.dp),
        containerColor = colorScheme.background,
        confirmButton = {
            when (confirmButtonStyle) {
                ConfirmButtonStyle.Filled -> {
                    Button(
                        onClick = onConfirm,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isDestructive) colorScheme.error else colorScheme.primary,
                        ),
                    ) {
                        Text(confirmText)
                    }
                }
                ConfirmButtonStyle.Outlined -> {
                    OutlinedButton(
                        onClick = onConfirm,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = if (isDestructive) colorScheme.error else colorScheme.primary,
                        ),
                    ) {
                        Text(confirmText)
                    }
                }
                ConfirmButtonStyle.Text -> {
                    TextButton(
                        onClick = onConfirm,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = if (isDestructive) colorScheme.error else colorScheme.primary,
                        ),
                    ) {
                        Text(confirmText)
                    }
                }
            }
        },
        dismissButton = onDismiss?.let {
            {
                when (confirmButtonStyle) {
                    ConfirmButtonStyle.Filled -> {
                        OutlinedButton(onClick = it) {
                            Text(dismissText)
                        }
                    }
                    ConfirmButtonStyle.Outlined, ConfirmButtonStyle.Text -> {
                        TextButton(onClick = it) {
                            Text(dismissText)
                        }
                    }
                }
            }
        },
    )
}

/**
 * 확인 버튼 스타일
 */
enum class ConfirmButtonStyle {
    /**
     * 배경색이 있는 버튼 (기본)
     */
    Filled,

    /**
     * 테두리만 있는 버튼
     */
    Outlined,

    /**
     * 텍스트만 있는 버튼
     */
    Text,
}
