package com.rebuilding.muscleatlas.client.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rebuilding.muscleatlas.design_system.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseButton
import com.rebuilding.muscleatlas.design_system.base.BaseText
import com.rebuilding.muscleatlas.design_system.component.BaseTopBar

@Composable
fun  ClientProfileTopBar(
    title: String,
    onClickBack: () -> Unit = {},
) {
    BaseTopBar(
        title = {
            BaseText(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = AppColors.onPrimary
            )
        },
        backIcon = {
            BaseButton(
                modifier = Modifier,
                icon = Icons.Default.KeyboardArrowLeft,
                iconSize = 30.dp,
            ) {
                onClickBack()
            }
        },
    )
}