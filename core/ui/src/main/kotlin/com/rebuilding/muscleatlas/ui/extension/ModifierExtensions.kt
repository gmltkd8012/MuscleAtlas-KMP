package com.rebuilding.muscleatlas.ui.extension

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role

@Composable
fun Modifier.clickableWithoutIndication(
    onClick: () -> Unit
): Modifier = this.clickable(
    indication = null,
    interactionSource = remember { MutableInteractionSource() },
    onClick = onClick
)

@Composable
fun Modifier.selectableWithoutIndication(
    selected: Boolean,
    onClick: () -> Unit,
    role: Role,
): Modifier = this.selectable(
    selected = selected,
    onClick = onClick,
    indication = null,
    interactionSource = remember { MutableInteractionSource() },
    role = role,
)