package com.rebuilding.muscleatlas.supabase.source

import com.rebuilding.muscleatlas.supabase.SessionDataSource
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.user.UserInfo
import io.github.jan.supabase.functions.functions
import io.github.jan.supabase.postgrest.from
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class SessionDataSourceImpl(
    private val supabaseClient: SupabaseClient,
): SessionDataSource {

    override suspend fun loadSessionFromStorage(): Result<Boolean> =
        runCatching {
            supabaseClient.auth.loadFromStorage()
        }

    override suspend fun getSessionUserInfo(): Result<UserInfo?> =
        runCatching {
            supabaseClient.auth.currentUserOrNull()
        }

    override suspend fun signOut(): Result<Unit> =
        runCatching {
            supabaseClient.auth.signOut()
        }

    override suspend fun deleteUser(requestBody: JsonObject): Result<Boolean> =
        runCatching {
            val response = supabaseClient.functions.invoke(
                function = "delete-user",
                body = requestBody,
            )

            val statusCode = response.status.value

            // 성공 응답 시, true 반환
            statusCode in 200..299
        }
}