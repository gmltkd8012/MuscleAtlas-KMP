package com.rebuilding.muscleatlas.data.repository

import com.rebuilding.muscleatlas.data.model.ExerciseGroup
import com.rebuilding.muscleatlas.data.model.ExerciseGroupInsert
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

interface ExerciseGroupRepository {
    /**
     * 모든 운동 그룹 조회 (created_at 오름차순)
     */
    fun getExerciseGroups(): Flow<List<ExerciseGroup>>

    /**
     * 운동 그룹 추가
     */
    suspend fun insertExerciseGroup(name: String): ExerciseGroup

    /**
     * 운동 그룹 삭제
     */
    suspend fun deleteExerciseGroup(id: String)
}

class ExerciseGroupRepositoryImpl(
    private val supabaseClient: SupabaseClient,
    private val ioDispatcher: CoroutineDispatcher,
) : ExerciseGroupRepository {

    companion object {
        private const val EXERCISE_GROUPS_TABLE = "exercise_groups"
    }

    override fun getExerciseGroups(): Flow<List<ExerciseGroup>> = flow {
        val groups = supabaseClient
            .from(EXERCISE_GROUPS_TABLE)
            .select {
                //order("created_at")
            }
            .decodeList<ExerciseGroup>()
        emit(groups)
    }.flowOn(ioDispatcher)

    override suspend fun insertExerciseGroup(name: String): ExerciseGroup = withContext(ioDispatcher) {
        supabaseClient
            .from(EXERCISE_GROUPS_TABLE)
            .insert(ExerciseGroupInsert(name = name)) {
                select()
            }
            .decodeSingle<ExerciseGroup>()
    }

    override suspend fun deleteExerciseGroup(id: String) {
        withContext(ioDispatcher) {
            supabaseClient
                .from(EXERCISE_GROUPS_TABLE)
                .delete {
                    filter {
                        eq("id", id)
                    }
                }
        }
    }
}
