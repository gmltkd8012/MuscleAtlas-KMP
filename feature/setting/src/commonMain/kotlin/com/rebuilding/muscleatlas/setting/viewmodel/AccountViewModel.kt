package com.rebuilding.muscleatlas.setting.viewmodel

import androidx.lifecycle.viewModelScope
import com.rebuilding.muscleatlas.ui.base.StateViewModel
import com.rebuilding.muscleatlas.ui.util.Logger
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime

class AccountViewModel(
    private val supabaseClient: SupabaseClient,
) : StateViewModel<AccountState, AccountSideEffect>(AccountState()) {

    init {
        loadAccountInfo()
    }

    @OptIn(ExperimentalTime::class)
    private fun loadAccountInfo() {
        viewModelScope.launch {
            val user = supabaseClient.auth.currentUserOrNull()
            user?.let {
                val provider = it.appMetadata?.get("provider")?.toString()
                    ?: it.identities?.firstOrNull()?.provider

                val createdAt = it.createdAt?.let { instant ->
                    formatDate(instant)
                }

                reduceState {
                    copy(
                        email = it.email,
                        provider = provider,
                        createdAt = createdAt,
                        userId = it.id,
                    )
                }
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            supabaseClient.auth.signOut()
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun formatDate(instant: Instant): String {
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        return "${localDateTime.year}년 ${localDateTime.monthNumber}월 ${localDateTime.dayOfMonth}일"
    }
}

data class AccountState(
    val email: String? = null,
    val provider: String? = null,
    val createdAt: String? = null,
    val userId: String? = null,
)

sealed interface AccountSideEffect
