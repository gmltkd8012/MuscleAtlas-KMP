package com.rebuilding.muscleatlas.main.screen

import android.R.attr.name
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rebuilding.muscleatlas.client.screen.ClientAddScreen
import com.rebuilding.muscleatlas.main.unit.ClientInfoChip
import com.rebuilding.muscleatlas.design_system.theme.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseLine
import com.rebuilding.muscleatlas.design_system.base.BaseText
import com.rebuilding.muscleatlas.design_system.component.BaseBottomSheet
import com.rebuilding.muscleatlas.main.viewmodel.ClientMainViewModel
import com.rebuilding.muscleatlas.model.Client

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientMainScreen(
    viewModel: ClientMainViewModel = hiltViewModel(),
    isShowBottomSheet: Boolean = false,
    onClickProfile: () -> Unit = {},
    onDismissBottomSheet: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()

    val listState = rememberLazyListState()

    if (state.clients.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            BaseText(
                text = "회원을 등록해 주세요.",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight(500)
                ),
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    } else {
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = listState
        ) {
            items(
                count = state.clients.size,
            ) { index ->
                ClientInfoChip(
                    name = state.clients[index].name,
                    memo = state.clients[index].memo,
                    onClick = onClickProfile,
                )

                if (index != state.clients.size - 1) {
                    BaseLine(
                        lineColor = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }

    if (isShowBottomSheet) {
        BaseBottomSheet(
            modifier = Modifier.fillMaxWidth(),
            sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true,
                confirmValueChange = { state ->
                    state != SheetValue.PartiallyExpanded
                },
            ),
            onDismissRequest = onDismissBottomSheet,
        ) {
            ClientAddScreen(
                onSave = { (name, memo) ->
                    viewModel.updateClient(
                        Client(
                            name = name,
                            memo = memo
                        )
                    )
                    onDismissBottomSheet()
                },
                onDismissRequest = onDismissBottomSheet
            )
        }
    }
}