package com.rebuilding.muscleatlas.data.repository

import com.rebuilding.muscleatlas.data.model.CreateMemberInviteRequest
import com.rebuilding.muscleatlas.data.model.MemberInvite
import com.rebuilding.muscleatlas.util.DateFormatter
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlin.random.Random

interface MemberInviteRepository {
    /**
     * 회원의 유효한 초대 코드 조회 (만료되지 않은 것)
     */
    suspend fun getValidInvite(memberId: String): MemberInvite?

    /**
     * 초대 코드로 초대 정보 조회
     */
    suspend fun getInviteByCode(inviteCode: String): MemberInvite?

    /**
     * 초대 코드 생성
     */
    suspend fun createInvite(memberId: String): MemberInvite

    /**
     * 초대 코드 생성 또는 기존 유효한 코드 반환
     */
    suspend fun getOrCreateInvite(memberId: String): MemberInvite
}

class MemberInviteRepositoryImpl(
    private val supabaseClient: SupabaseClient,
    private val ioDispatcher: CoroutineDispatcher,
) : MemberInviteRepository {

    companion object {
        private const val TABLE_NAME = "member_invite"
        private const val CODE_LENGTH = 8
        private const val CODE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    }

    override suspend fun getValidInvite(memberId: String): MemberInvite? = withContext(ioDispatcher) {
        val currentTime = DateFormatter.getCurrentTimeMillis()
        
        supabaseClient
            .from(TABLE_NAME)
            .select {
                filter {
                    eq("member_id", memberId)
                    gt("expires_at", currentTime)
                }
            }
            .decodeSingleOrNull<MemberInvite>()
    }

    override suspend fun getInviteByCode(inviteCode: String): MemberInvite? = withContext(ioDispatcher) {
        val currentTime = DateFormatter.getCurrentTimeMillis()
        
        supabaseClient
            .from(TABLE_NAME)
            .select {
                filter {
                    eq("invite_code", inviteCode)
                    gt("expires_at", currentTime)
                }
            }
            .decodeSingleOrNull<MemberInvite>()
    }

    override suspend fun createInvite(memberId: String): MemberInvite = withContext(ioDispatcher) {
        val userId = supabaseClient.auth.currentUserOrNull()?.id
            ?: throw IllegalStateException("User not logged in")
        
        val inviteCode = generateInviteCode()
        
        val request = CreateMemberInviteRequest(
            memberId = memberId,
            inviteCode = inviteCode,
            createdBy = userId,
        )
        
        supabaseClient
            .from(TABLE_NAME)
            .insert(request) {
                select()
            }
            .decodeSingle<MemberInvite>()
    }

    override suspend fun getOrCreateInvite(memberId: String): MemberInvite = withContext(ioDispatcher) {
        // 기존 유효한 초대가 있으면 반환
        getValidInvite(memberId)?.let { return@withContext it }
        
        // 없으면 새로 생성
        createInvite(memberId)
    }

    /**
     * 8자리 랜덤 초대 코드 생성
     */
    private fun generateInviteCode(): String {
        return (1..CODE_LENGTH)
            .map { CODE_CHARS[Random.nextInt(CODE_CHARS.length)] }
            .joinToString("")
    }
}
