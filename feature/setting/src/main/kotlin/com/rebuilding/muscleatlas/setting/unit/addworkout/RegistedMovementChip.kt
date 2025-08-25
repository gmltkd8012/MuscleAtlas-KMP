package com.rebuilding.muscleatlas.setting.unit.addworkout

import android.R.attr.onClick
import android.R.attr.text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rebuilding.muscleatlas.design_system.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseImageButton
import com.rebuilding.muscleatlas.design_system.base.BaseLine
import com.rebuilding.muscleatlas.design_system.base.BaseText
import com.rebuilding.muscleatlas.setting.unit.workoutmanage.WorkoutManageChip

@Composable
fun RegistedMovementChip(
    movementList: List<String> = emptyList<String>(),
    onClickEdit: () -> Unit = {},
    onClickAdd: () -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        BaseText(
            text = "세부 동작",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = AppColors.onPrimary,
            textAlign = TextAlign.Start
        )

        Spacer(Modifier.height(8.dp))

        if (movementList.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                BaseText(
                    text = "세부 동작을 등록해 주세요.",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight(500)
                    ),
                    color = AppColors.onSecondary
                )
            }
        } else {
            movementList.forEachIndexed { index, name ->
                WorkoutManageChip(
                    name = name,
                    modifier = Modifier.padding(vertical = 4.dp),
                    onClick = onClickEdit
                )

                if (index != movementList.lastIndex) {
                    BaseLine(
                        lineColor = AppColors.secondary
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        BaseImageButton(
            modifier = Modifier.align(Alignment.End),
            text = "종목 추가",
            icon = Icons.Default.Add,
            color = AppColors.onPrimary.copy(alpha = 0.1f),
            onClick = onClickAdd
        )
    }
}
