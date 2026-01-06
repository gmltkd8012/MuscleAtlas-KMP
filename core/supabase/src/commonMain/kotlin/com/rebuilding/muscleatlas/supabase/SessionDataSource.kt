package com.rebuilding.muscleatlas.supabase

interface SessionDataSource {

    suspend fun loadSessionFromStorage(): Result<Boolean>

    suspend fun signOut(): Result<Unit>
}