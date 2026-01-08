package com.rebuilding.muscleatlas.supabase

import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.serialization.json.JsonObject

interface SessionDataSource {

    suspend fun loadSessionFromStorage(): Result<Boolean>

    suspend fun getSessionUserInfo(): Result<UserInfo?>

    suspend fun signOut(): Result<Unit>

    suspend fun deleteUser(userId: JsonObject): Result<Boolean>
}