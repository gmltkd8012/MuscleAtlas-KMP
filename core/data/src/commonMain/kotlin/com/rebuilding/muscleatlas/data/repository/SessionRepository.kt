package com.rebuilding.muscleatlas.data.repository

import com.rebuilding.muscleatlas.supabase.SessionDataSource
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

interface SessionRepository {
    /**
     * 저장소에서 세션 로드
     * @return 세션 로드 성공 여부
     */
    suspend fun loadSessionFromStorage(): Result<Boolean>

    /**
     * 세션 사용자 정보 가져오기
     * @return 로그인 된 사용자 정보
     */
    suspend fun getSessionUserInfo(): Result<UserInfo?>

    /**
     * 로그아웃
     */
    suspend fun signOut(): Result<Unit>

    /**
     * 회원 탈퇴
     * @param 사용자 ID
     * @return 탈퇴 성공 여부
     */
    suspend fun deleteUser(userId: String?): Result<Boolean>
}

class SessionRepositoryImpl(
    private val sessionDataSource: SessionDataSource,
    private val ioDispatcher: CoroutineDispatcher,
) : SessionRepository {

    override suspend fun loadSessionFromStorage(): Result<Boolean> =
        withContext(ioDispatcher) {
            sessionDataSource.loadSessionFromStorage()
        }

    override suspend fun getSessionUserInfo(): Result<UserInfo?> =
        withContext(ioDispatcher) {
            sessionDataSource.getSessionUserInfo()
        }

    override suspend fun signOut(): Result<Unit> =
        withContext(ioDispatcher) {
            sessionDataSource.signOut()
        }

    override suspend fun deleteUser(userId: String?): Result<Boolean> =
        withContext(ioDispatcher) {
            val requestBody = buildJsonObject {
                put("user_id", userId)
            }

            sessionDataSource.deleteUser(requestBody)
        }
}
