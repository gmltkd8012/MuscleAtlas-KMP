package com.rebuilding.muscleatlas.supabase.source

import com.rebuilding.muscleatlas.supabase.SessionDataSource
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth

class SessionDataSourceImpl(
    private val supabaseClient: SupabaseClient,
): SessionDataSource {

    override suspend fun loadSessionFromStorage(): Result<Boolean> =
        runCatching {
            supabaseClient.auth.loadFromStorage()
        }

    override suspend fun signOut(): Result<Unit> =
        runCatching {
            supabaseClient.auth.signOut()
        }
}