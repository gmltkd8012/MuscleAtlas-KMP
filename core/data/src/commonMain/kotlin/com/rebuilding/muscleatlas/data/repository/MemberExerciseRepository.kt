package com.rebuilding.muscleatlas.data.repository

import com.rebuilding.muscleatlas.data.model.MemberExercise
import com.rebuilding.muscleatlas.data.model.MemberExerciseInsert
import com.rebuilding.muscleatlas.data.model.MemberExerciseUpdate
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

interface MemberExerciseRepository {
    /**
     * 특정 회원의 모든 운동 가능 여부 조회
     */
    fun getMemberExercises(memberId: String): Flow<List<MemberExercise>>

    /**
     * 특정 회원-운동 조회
     */
    suspend fun getMemberExercise(memberId: String, exerciseId: String): MemberExercise?

    /**
     * 회원-운동 일괄 생성 (회원 생성 시 모든 운동에 대해 호출)
     */
    suspend fun createMemberExercises(memberExercises: List<MemberExerciseInsert>)

    /**
     * 회원-운동 가능 여부 수정
     */
    suspend fun updateMemberExercise(id: String, update: MemberExerciseUpdate): MemberExercise

    /**
     * 특정 회원의 모든 운동 데이터 삭제
     */
    suspend fun deleteMemberExercises(memberId: String)
    
    /**
     * 특정 운동에 대한 모든 회원 데이터 삭제 (운동 삭제 시)
     */
    suspend fun deleteByExerciseId(exerciseId: String)
}

class MemberExerciseRepositoryImpl(
    private val supabaseClient: SupabaseClient,
    private val ioDispatcher: CoroutineDispatcher,
) : MemberExerciseRepository {

    companion object {
        private const val TABLE_NAME = "member_exercises"
    }

    override fun getMemberExercises(memberId: String): Flow<List<MemberExercise>> = flow {
        val memberExercises = supabaseClient
            .from(TABLE_NAME)
            .select {
                filter {
                    eq("member_id", memberId)
                }
            }
            .decodeList<MemberExercise>()
        emit(memberExercises)
    }.flowOn(ioDispatcher)

    override suspend fun getMemberExercise(
        memberId: String,
        exerciseId: String,
    ): MemberExercise? = withContext(ioDispatcher) {
        supabaseClient
            .from(TABLE_NAME)
            .select {
                filter {
                    eq("member_id", memberId)
                    eq("exercise_id", exerciseId)
                }
            }
            .decodeSingleOrNull<MemberExercise>()
    }

    override suspend fun createMemberExercises(memberExercises: List<MemberExerciseInsert>) {
        if (memberExercises.isEmpty()) return
        
        withContext(ioDispatcher) {
            supabaseClient
                .from(TABLE_NAME)
                .insert(memberExercises)
        }
    }

    override suspend fun updateMemberExercise(
        id: String,
        update: MemberExerciseUpdate,
    ): MemberExercise = withContext(ioDispatcher) {
        supabaseClient
            .from(TABLE_NAME)
            .update(update) {
                select()
                filter {
                    eq("id", id)
                }
            }
            .decodeSingle<MemberExercise>()
    }

    override suspend fun deleteMemberExercises(memberId: String) {
        withContext(ioDispatcher) {
            supabaseClient
                .from(TABLE_NAME)
                .delete {
                    filter {
                        eq("member_id", memberId)
                    }
                }
        }
    }
    
    override suspend fun deleteByExerciseId(exerciseId: String) {
        withContext(ioDispatcher) {
            supabaseClient
                .from(TABLE_NAME)
                .delete {
                    filter {
                        eq("exercise_id", exerciseId)
                    }
                }
        }
    }
}
