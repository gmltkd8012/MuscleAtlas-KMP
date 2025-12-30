package com.rebuilding.muscleatlas.member.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.rebuilding.muscleatlas.member.component.MemberListItem
import com.rebuilding.muscleatlas.member.viewmodel.MemberViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MemberScreen(
    viewModel: MemberViewModel = koinViewModel(),
    onNavigateToDetail: () -> Unit,
) {
    val testMembers = remember {
        (1..20).map { "회원 $it" }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {

        //TODO - 실제 데이터 연동 해야함
        items(testMembers) { member ->
            MemberListItem(
                title = member,
                onClick = onNavigateToDetail,
            )
        }
    }
}
