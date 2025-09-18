package com.rebuilding.muscleatlas.client.unit

import android.R.attr.onClick
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rebuilding.muscleatlas.design_system.base.BaseText
import com.rebuilding.muscleatlas.design_system.base.SquaredImageBox
import com.rebuilding.muscleatlas.design_system.component.BaseCircleCheckBox
import com.rebuilding.muscleatlas.ui.extension.clickableWithoutIndication

@Composable
fun MovementBox(
    name: String,
    imgUrl: String? = null,
    size: Dp = 200.dp,
    isChecked: Boolean,
    onClickCheckBox:(Boolean) -> Unit,
    onClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier.wrapContentSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Box(
            modifier = Modifier.wrapContentSize()
        ) {
            SquaredImageBox(
                size = size,
                onClick = onClick
            )

            BaseCircleCheckBox(
                modifier = Modifier
                    .size(36.dp)
                    .align(Alignment.TopEnd),
                isChecked = isChecked,
                onClickCheckBox = onClickCheckBox
            )
        }

        Spacer(Modifier.height(8.dp))

        BaseText(
            text = name,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 16.sp,
            ),
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Preview
@Composable
fun MovementBoxPreview() {
    MovementBox(
        name = "세부 동작 1",
        isChecked = false,
        onClickCheckBox = {},
    )
}