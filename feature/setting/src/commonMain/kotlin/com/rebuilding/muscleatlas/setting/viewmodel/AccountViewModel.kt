package com.rebuilding.muscleatlas.setting.viewmodel

import androidx.lifecycle.viewModelScope
import com.rebuilding.muscleatlas.data.repository.SessionRepository
import com.rebuilding.muscleatlas.ui.base.StateViewModel
import com.rebuilding.muscleatlas.ui.util.Logger
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.functions.functions
import io.ktor.client.call.body
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlin.time.ExperimentalTime

class AccountViewModel(
    private val supabaseClient: SupabaseClient,
    private val sessionRepository: SessionRepository,
) : StateViewModel<AccountState, AccountSideEffect>(AccountState()) {

    init {
        loadAccountInfo()
        observeSessionState()
    }

    private fun observeSessionState() {
        supabaseClient.auth.sessionStatus
            .onEach { status ->
                Logger.d("AccountViewModel", "SessionStatus: $status")
                when (status) {
                    is SessionStatus.NotAuthenticated -> {
                        reduceState { copy(isDeleting = false, isLogout = false, isDeleteSuccess = true) }
                        sendSideEffect(AccountSideEffect.NotAuthenticated)
                    }
                    else -> Unit
                }
            }
            .launchIn(this)
    }


    @OptIn(ExperimentalTime::class)
    private fun loadAccountInfo() {
        viewModelScope.launch {
            val user = sessionRepository.getSessionUserInfo().getOrNull()

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

    internal fun signOut() {
        viewModelScope.launch {
            reduceState { copy(isLogout = true, deleteError = null) }
            sessionRepository.signOut()
        }
    }

    internal fun deleteAccount() {
        viewModelScope.launch {
            reduceState { copy(isDeleting = true, deleteError = null) }

            try {
                val userId = state.value.userId
                if (userId == null) {
                    reduceState { copy(isDeleting = false, deleteError = "사용자 정보를 찾을 수 없습니다.") }
                    return@launch
                }

                val isDeleteSuccessed = sessionRepository.deleteUser(userId).getOrDefault(false)

                if (isDeleteSuccessed) {
                    // 로그아웃 처리
                    signOut()
                } else {
                    reduceState {
                        copy(
                            isDeleting = false,
                            deleteError = "회원 탈퇴에 실패했습니다."
                        )
                    }
                }
            } catch (e: Exception) {
                Logger.e(TAG, "회원 탈퇴 실패: ${e.message}")
                reduceState {
                    copy(
                        isDeleting = false,
                        deleteError = "회원 탈퇴 중 오류가 발생했습니다."
                    )
                }
            }
        }
    }

    fun clearDeleteError() {
        viewModelScope.launch {
            reduceState { copy(deleteError = null) }
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun formatDate(instant: Instant): String {
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        return "${localDateTime.year}년 ${localDateTime.monthNumber}월 ${localDateTime.dayOfMonth}일"
    }

    companion object {
        private const val TAG = "AccountViewModel"
    }
}

data class AccountState(
    val email: String? = null,
    val provider: String? = null,
    val createdAt: String? = null,
    val userId: String? = null,
    val isDeleting: Boolean = false,
    val isLogout: Boolean = false,
    val isDeleteSuccess: Boolean = false,
    val deleteError: String? = null,
)

sealed interface AccountSideEffect {
    data object NotAuthenticated : AccountSideEffect
}
