package com.rebuilding.muscleatlas.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rebuilding.muscleatlas.design_system.AppColors
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseBottomSheet(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
    onDismissRequest: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(),
    isDragHandle: Boolean = true,
    content: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        modifier = Modifier,
        onDismissRequest = {
            scope.launch {
                sheetState.hide()
                onDismissRequest()
            }
        },
        sheetState = sheetState,
        shape = shape,
        dragHandle = {
            if (isDragHandle) {
                SheetHandler()
            } else null
        },
        content = {
            content()
        }
    )
}

@Composable
fun SheetHandler(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.color.secondary),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .width(56.dp)
                .height(5.dp)
                .background(AppColors.color.onPrimary, RoundedCornerShape(100))
        )
    }
}