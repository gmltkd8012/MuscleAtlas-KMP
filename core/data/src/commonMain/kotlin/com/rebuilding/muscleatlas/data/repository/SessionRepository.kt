package com.rebuilding.muscleatlas.data.repository

import com.rebuilding.muscleatlas.supabase.SessionDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface SessionRepository {
    /**
     * 저장소에서 세션 로드
     * @return 세션 로드 성공 여부
     */
    suspend fun loadSessionFromStorage(): Result<Boolean>

    /**
     * 로그아웃
     */
    suspend fun signOut(): Result<Unit>
}

class SessionRepositoryImpl(
    private val sessionDataSource: SessionDataSource,
    private val ioDispatcher: CoroutineDispatcher,
) : SessionRepository {

    override suspend fun loadSessionFromStorage(): Result<Boolean> =
        withContext(ioDispatcher) {
            sessionDataSource.loadSessionFromStorage()
        }

    override suspend fun signOut(): Result<Unit> =
        withContext(ioDispatcher) {
            sessionDataSource.signOut()
        }
}
