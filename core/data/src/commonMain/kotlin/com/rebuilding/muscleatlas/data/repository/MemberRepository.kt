package com.rebuilding.muscleatlas.data.repository

import com.rebuilding.muscleatlas.data.model.CreateMemberRequest
import com.rebuilding.muscleatlas.data.model.Member
import com.rebuilding.muscleatlas.data.model.MemberTagData
import com.rebuilding.muscleatlas.data.model.UpdateMemberRequest
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

interface MemberRepository {
    /**
     * 모든 회원 목록 조회
     */
    fun getMembers(): Flow<List<Member>>

    /**
     * 특정 회원 조회
     */
    suspend fun getMember(id: String): Member?

    /**
     * 회원 추가
     */
    suspend fun createMember(request: CreateMemberRequest): Member

    /**
     * 회원 정보 수정
     */
    suspend fun updateMember(id: String, request: UpdateMemberRequest): Member

    /**
     * 회원 태그 업데이트
     */
    suspend fun updateMemberTags(id: String, tags: List<MemberTagData>): Member

    /**
     * 회원 삭제
     */
    suspend fun deleteMember(id: String)
}

class MemberRepositoryImpl(
    private val supabaseClient: SupabaseClient,
    private val ioDispatcher: CoroutineDispatcher,
) : MemberRepository {

    companion object {
        private const val TABLE_NAME = "member"
    }

    override fun getMembers(): Flow<List<Member>> = flow {
        val userId = supabaseClient.auth.currentUserOrNull()?.id
        val members = if (userId != null) {
            supabaseClient
                .from(TABLE_NAME)
                .select {
                    filter {
                        eq("user_id", userId)
                    }
                }
                .decodeList<Member>()
        } else {
            emptyList()
        }
        emit(members)
    }.flowOn(ioDispatcher)

    override suspend fun getMember(id: String): Member? = withContext(ioDispatcher) {
        supabaseClient
            .from(TABLE_NAME)
            .select {
                filter {
                    eq("id", id)
                }
            }
            .decodeSingleOrNull<Member>()
    }

    override suspend fun createMember(request: CreateMemberRequest): Member =
        withContext(ioDispatcher) {
            supabaseClient
                .from(TABLE_NAME)
                .insert(request) {
                    select()
                }
                .decodeSingle<Member>()
        }

    override suspend fun updateMember(id: String, request: UpdateMemberRequest): Member =
        withContext(ioDispatcher) {
            supabaseClient
                .from(TABLE_NAME)
                .update(request) {
                    select()
                    filter {
                        eq("id", id)
                    }
                }
                .decodeSingle<Member>()
        }

    override suspend fun updateMemberTags(id: String, tags: List<MemberTagData>): Member =
        withContext(ioDispatcher) {
            supabaseClient
                .from(TABLE_NAME)
                .update(UpdateMemberRequest(tags = tags)) {
                    select()
                    filter {
                        eq("id", id)
                    }
                }
                .decodeSingle<Member>()
        }

    override suspend fun deleteMember(id: String) {
        withContext(ioDispatcher) {
            supabaseClient
                .from(TABLE_NAME)
                .delete {
                    filter {
                        eq("id", id)
                    }
                }
        }
    }
}
