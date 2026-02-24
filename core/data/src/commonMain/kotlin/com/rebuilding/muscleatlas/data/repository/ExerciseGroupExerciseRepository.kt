package com.rebuilding.muscleatlas.data.repository

import com.rebuilding.muscleatlas.data.model.Exercise
import com.rebuilding.muscleatlas.data.model.ExerciseGroupExercise
import com.rebuilding.muscleatlas.data.model.ExerciseGroupExerciseInsert
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

interface ExerciseGroupExerciseRepository {
    /**
     * 특정 그룹에 속한 운동 목록 조회
     */
    fun getExercisesInGroup(groupId: String): Flow<List<Exercise>>

    /**
     * 운동들을 그룹에 추가
     * 이미 다른 그룹에 속한 운동은 현재 그룹으로 이동
     */
    suspend fun addExercisesToGroup(groupId: String, exerciseIds: List<String>)

    /**
     * 그룹에서 특정 운동 제거
     */
    suspend fun removeExerciseFromGroup(groupId: String, exerciseId: String)

    /**
     * 그룹의 모든 운동 제거
     */
    suspend fun clearGroup(groupId: String)

    /**
     * 특정 운동이 속한 그룹 ID 조회
     */
    suspend fun getGroupForExercise(exerciseId: String): String?
}

class ExerciseGroupExerciseRepositoryImpl(
    private val supabaseClient: SupabaseClient,
    private val ioDispatcher: CoroutineDispatcher,
) : ExerciseGroupExerciseRepository {

    companion object {
        private const val TABLE = "exercise_group_exercises"
        private const val EXERCISES_TABLE = "exercises"
    }

    override fun getExercisesInGroup(groupId: String): Flow<List<Exercise>> = flow {
        // 1. 그룹에 속한 exercise_id들을 먼저 조회
        val groupExercises = supabaseClient
            .from(TABLE)
            .select {
                filter {
                    eq("group_id", groupId)
                }
            }
            .decodeList<ExerciseGroupExercise>()

        // 2. exercise_id 목록 추출
        val exerciseIds = groupExercises.map { it.exerciseId }

        // 3. exercise_id들로 실제 Exercise 객체들을 조회
        if (exerciseIds.isEmpty()) {
            emit(emptyList())
        } else {
            val exercises = supabaseClient
                .from(EXERCISES_TABLE)
                .select {
                    filter {
                        isIn("id", exerciseIds)
                    }
                }
                .decodeList<Exercise>()
            emit(exercises)
        }
    }.flowOn(ioDispatcher)

    override suspend fun addExercisesToGroup(groupId: String, exerciseIds: List<String>) {
        if (exerciseIds.isEmpty()) return

        withContext(ioDispatcher) {
            exerciseIds.forEach { exerciseId ->
                // 기존 그룹에서 삭제 (다른 그룹에 속해있을 수 있음)
                supabaseClient
                    .from(TABLE)
                    .delete {
                        filter {
                            eq("exercise_id", exerciseId)
                        }
                    }

                // 현재 그룹에 추가
                supabaseClient
                    .from(TABLE)
                    .insert(
                        ExerciseGroupExerciseInsert(
                            groupId = groupId,
                            exerciseId = exerciseId
                        )
                    )
            }
        }
    }

    override suspend fun removeExerciseFromGroup(groupId: String, exerciseId: String) {
        withContext(ioDispatcher) {
            supabaseClient
                .from(TABLE)
                .delete {
                    filter {
                        eq("group_id", groupId)
                        eq("exercise_id", exerciseId)
                    }
                }
        }
    }

    override suspend fun clearGroup(groupId: String) {
        withContext(ioDispatcher) {
            supabaseClient
                .from(TABLE)
                .delete {
                    filter {
                        eq("group_id", groupId)
                    }
                }
        }
    }

    override suspend fun getGroupForExercise(exerciseId: String): String? = withContext(ioDispatcher) {
        supabaseClient
            .from(TABLE)
            .select {
                filter {
                    eq("exercise_id", exerciseId)
                }
            }
            .decodeSingleOrNull<ExerciseGroupExercise>()
            ?.groupId
    }
}
