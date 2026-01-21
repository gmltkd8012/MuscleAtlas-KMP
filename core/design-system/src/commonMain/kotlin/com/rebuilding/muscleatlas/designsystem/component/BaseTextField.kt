package com.rebuilding.muscleatlas.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun BaseTextField(
    modifier: Modifier = Modifier,
    value: String,
    labelText: String,
    hintText: String,
    singleLine: Boolean = true,
    maxLine: Int = 5,
    minLine: Int = 3,
    onValueChanged: (String) -> Unit,
    onDelete: () -> Unit,
) {
    // Primary 필드
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChanged(it) },
        label = { Text(labelText) },
        placeholder = { Text(hintText) },
        modifier = modifier.fillMaxWidth(),
        singleLine = singleLine,
        maxLines = if (singleLine) 1 else maxLine,
        minLines = if (singleLine) 1 else minLine,
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "전체 삭제",
                        tint = colorScheme.onSurfaceVariant,
                    )
                }
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = colorScheme.onBackground,
            unfocusedTextColor = colorScheme.onBackground,
        ),
    )
}