package com.rebuilding.muscleatlas.main.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rebuilding.muscleatlas.client.screen.ClientBottomSheetScreen
import com.rebuilding.muscleatlas.main.unit.ClientInfoChip
import com.rebuilding.muscleatlas.design_system.base.BaseLine
import com.rebuilding.muscleatlas.design_system.base.BaseText
import com.rebuilding.muscleatlas.design_system.component.BaseBottomSheet
import com.rebuilding.muscleatlas.main.component.MainHeaderBar
import com.rebuilding.muscleatlas.main.viewmodel.ClientMainSideEffect
import com.rebuilding.muscleatlas.main.viewmodel.ClientMainViewModel
import com.rebuilding.muscleatlas.model.Screen
import com.rebuilding.muscleatlas.ui.extension.hide
import com.rebuilding.muscleatlas.ui.extension.isShown
import com.rebuilding.muscleatlas.ui.extension.rememberClientBottomSheetState
import com.rebuilding.muscleatlas.ui.extension.show

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientMainScreen(
    viewModel: ClientMainViewModel = hiltViewModel(),
    onClickProfile: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()
    val listState = rememberLazyListState()

    val bottomSheetState = rememberClientBottomSheetState<ClientMainSideEffect.ShowClientAddBottomSheet>()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is ClientMainSideEffect.ShowClientAddBottomSheet -> {
                bottomSheetState.show(
                    client = sideEffect.client
                )
            }
        }
    }

    Scaffold(
        topBar = {
            MainHeaderBar(
                title = Screen.Client.label,
                isNeedAdd = true,
                onClickAdd = viewModel::showBottomSheet
            )
        },
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.primary)
        ) {
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
                var swipeItemId by remember { mutableStateOf<String?>(null) }

                LazyColumn (
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    state = listState
                ) {
                    itemsIndexed(
                        items = state.clients,
                        key = { _, client -> client.id }
                    ) { index, client ->
                        ClientInfoChip(
                            client = client,
                            swipedItemId = swipeItemId,
                            onSwipe = { id -> swipeItemId = id },
                            onClick = onClickProfile,
                            onDelete = { id ->
                                viewModel.deleteClient(id)
                            },
                            onEdit = { client ->
                                bottomSheetState.show(client)
                            }
                        )

                        if (index != state.clients.size - 1) {
                            BaseLine(
                                lineColor = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                }
            }
        }
    }

    if (bottomSheetState.isShown) {
        BaseBottomSheet(
            modifier = Modifier.fillMaxWidth(),
            sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true,
                confirmValueChange = { state ->
                    state != SheetValue.PartiallyExpanded
                },
            ),
            onDismissRequest = {
                bottomSheetState.hide()
            },
        ) {
            ClientBottomSheetScreen(
                client = bottomSheetState.value,
                onSave = { client ->
                    viewModel.updateClient(client)
                    bottomSheetState.hide()
                },
                onDismissRequest = {
                    bottomSheetState.hide()
                }
            )
        }
    }
}