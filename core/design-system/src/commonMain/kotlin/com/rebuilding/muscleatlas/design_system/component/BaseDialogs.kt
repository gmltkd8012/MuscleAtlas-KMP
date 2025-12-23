package com.rebuilding.muscleatlas.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.rebuilding.muscleatlas.design_system.base.BaseText
import com.rebuilding.muscleatlas.design_system.theme.AppColors

@Composable
fun DialogBase(
    title: String?,
    content: String?,
    contentTextAlign : TextAlign = TextAlign.Start
) {
    Column(
        modifier = Modifier.padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        title?.let {
            BaseText(
                modifier = Modifier,
                text = it,
                style = MaterialTheme.typography.titleMedium.copy(
                    lineHeight = 28.sp,
                ),
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center
            )
        }

        content?.let {
            BaseText(
                text = it,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = contentTextAlign
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun VerticalDialog(
    title: String = "제목",
    description: String? = null,
    leftButtonText: String = "취소",
    rightButtonText: String = "확인",
    contentTextAlign: TextAlign = TextAlign.Start,
    dialogProperties : DialogProperties = DialogProperties(),
    leftButton: () -> Unit = {},
    rightButton: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    val up = Pair(leftButtonText, leftButton)
    val down = Pair(rightButtonText, rightButton)

    Dialog(
        properties = dialogProperties,
        onDismissRequest = { onDismiss.invoke() }
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .shadow(
                    elevation = 16.dp,
                    spotColor = AppColors.Shadow.Dialog,
                    shape = RoundedCornerShape(12.dp), // 그림자도 라운드 처리
                    ambientColor = AppColors.Shadow.Dialog
                )
                .background(color = MaterialTheme.colorScheme.primary)
        ) {
            Column(
                modifier = Modifier
                    .padding(bottom = 20.dp, start = 20.dp, end = 20.dp)
                    .width(310.dp)
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                DialogBase(
                    title = title,
                    content = description,
                    contentTextAlign = contentTextAlign
                )

                Spacer(modifier = Modifier.padding(top = 20.dp))

                val size = Modifier.size(width = 131.dp, height = 52.dp)
                HorizontalButtons(size, up, down,)
            }
        }
    }
}

@Composable
fun HorizontalButtons(
    modifier: Modifier = Modifier,
    left: Pair<String, () -> Unit>,
    right: Pair<String, () -> Unit>,
    isDisable: Boolean = false
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        NormalButton(modifier = modifier, text = left.first, onClick = { left.second.invoke() })
        PrimaryButton(modifier = modifier, text = right.first, onClick = { right.second.invoke() })
    }
}