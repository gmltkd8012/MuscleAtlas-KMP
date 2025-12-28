package com.rebuilding.muscleatlas.member.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.rebuilding.muscleatlas.member.viewmodel.MemberViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MemberScreen(
    viewModel: MemberViewModel = koinViewModel(),
) {
    val supabaseClient = koinInject<SupabaseClient>()
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "회원 관리",
            modifier = Modifier.clickable {
                scope.launch {
                    supabaseClient.auth.signOut()
                }
            }
        )
    }
}
