package com.rebuilding.muscleatlas.data.repository

import com.rebuilding.muscleatlas.data.model.Exercise
import com.rebuilding.muscleatlas.data.model.ExerciseDetail
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

interface ExerciseRepository {
    /**
     * 모든 운동 종목 조회
     */
    fun getExercises(): Flow<List<Exercise>>

    /**
     * 특정 운동의 상세 정보 조회
     */
    fun getExerciseDetails(exerciseId: String): Flow<List<ExerciseDetail>>

    /**
     * 특정 운동 조회
     */
    suspend fun getExercise(exerciseId: String): Exercise?
    
    /**
     * 운동 상세 정보 업데이트
     */
    suspend fun updateExerciseDetail(detail: ExerciseDetail)
    
    /**
     * 여러 운동 상세 정보 일괄 업데이트
     */
    suspend fun updateExerciseDetails(details: List<ExerciseDetail>)
}

class ExerciseRepositoryImpl(
    private val supabaseClient: SupabaseClient,
    private val ioDispatcher: CoroutineDispatcher,
) : ExerciseRepository {

    companion object {
        private const val EXERCISES_TABLE = "exercises"
        private const val EXERCISE_DETAILS_TABLE = "exercise_details"
    }

    override fun getExercises(): Flow<List<Exercise>> = flow {
        val exercises = supabaseClient
            .from(EXERCISES_TABLE)
            .select()
            .decodeList<Exercise>()
        emit(exercises)
    }.flowOn(ioDispatcher)

    override fun getExerciseDetails(exerciseId: String): Flow<List<ExerciseDetail>> = flow {
        val details = supabaseClient
            .from(EXERCISE_DETAILS_TABLE)
            .select {
                filter {
                    eq("exercise_id", exerciseId)
                }
            }
            .decodeList<ExerciseDetail>()
        emit(details)
    }.flowOn(ioDispatcher)

    override suspend fun getExercise(exerciseId: String): Exercise? = withContext(ioDispatcher) {
        supabaseClient
            .from(EXERCISES_TABLE)
            .select {
                filter {
                    eq("id", exerciseId)
                }
            }
            .decodeSingleOrNull<Exercise>()
    }
    
    override suspend fun updateExerciseDetail(detail: ExerciseDetail) {
        withContext(ioDispatcher) {
            supabaseClient
                .from(EXERCISE_DETAILS_TABLE)
                .update(detail) {
                    filter {
                        eq("id", detail.id)
                    }
                }
        }
    }
    
    override suspend fun updateExerciseDetails(details: List<ExerciseDetail>) {
        withContext(ioDispatcher) {
            details.forEach { detail ->
                supabaseClient
                    .from(EXERCISE_DETAILS_TABLE)
                    .update(detail) {
                        filter {
                            eq("id", detail.id)
                        }
                    }
            }
        }
    }
}
