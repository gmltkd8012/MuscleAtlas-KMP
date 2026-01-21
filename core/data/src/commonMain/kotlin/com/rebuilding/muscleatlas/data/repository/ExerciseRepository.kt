package com.rebuilding.muscleatlas.data.repository

import com.rebuilding.muscleatlas.data.model.Exercise
import com.rebuilding.muscleatlas.data.model.ExerciseDetail
import com.rebuilding.muscleatlas.data.model.ExerciseDetailInsert
import com.rebuilding.muscleatlas.data.model.ExerciseInsert
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
    
    /**
     * 운동 추가
     */
    suspend fun insertExercise(name: String): Exercise
    
    /**
     * 운동 상세 정보 일괄 추가
     */
    suspend fun insertExerciseDetails(details: List<ExerciseDetailInsert>)

    /**
     * 운동 이미지 URL 업데이트
     */
    suspend fun updateExerciseImageUrl(exerciseId: String, exerciseImg: String?): Exercise
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
    
    override suspend fun insertExercise(name: String): Exercise = withContext(ioDispatcher) {
        // 1. Exercise 생성
        val exercise = supabaseClient
            .from(EXERCISES_TABLE)
            .insert(ExerciseInsert(name = name)) {
                select()
            }
            .decodeSingle<Exercise>()
        
        // 2. 기본 exercise_details 템플릿 생성
        val defaultDetails = createDefaultExerciseDetails(exercise.id)
        insertExerciseDetails(defaultDetails)
        
        exercise
    }
    
    override suspend fun insertExerciseDetails(details: List<ExerciseDetailInsert>) {
        withContext(ioDispatcher) {
            supabaseClient
                .from(EXERCISE_DETAILS_TABLE)
                .insert(details)
        }
    }

    override suspend fun updateExerciseImageUrl(
        exerciseId: String,
        exerciseImg: String?
    ): Exercise = withContext(ioDispatcher) {
        supabaseClient
            .from(EXERCISES_TABLE)
            .update(mapOf("exercise_img" to exerciseImg)) {
                select()
                filter { eq("id", exerciseId) }
            }
            .decodeSingle<Exercise>()
    }

    /**
     * 새 운동에 대한 기본 exercise_details 템플릿 생성
     */
    private fun createDefaultExerciseDetails(exerciseId: String): List<ExerciseDetailInsert> {
        return listOf(
            // 기계적 움직임 - Eccentric (하강)
            ExerciseDetailInsert(
                exerciseId = exerciseId,
                movementType = "기계적 움직임",
                contractionType = "Eccentric (하강)",
                detailCategory = "Primary",
                description = null,
            ),
            ExerciseDetailInsert(
                exerciseId = exerciseId,
                movementType = "기계적 움직임",
                contractionType = "Eccentric (하강)",
                detailCategory = "Secondary",
                description = null,
            ),
            ExerciseDetailInsert(
                exerciseId = exerciseId,
                movementType = "기계적 움직임",
                contractionType = "Eccentric (하강)",
                detailCategory = "근위/원위",
                description = null,
            ),
            ExerciseDetailInsert(
                exerciseId = exerciseId,
                movementType = "기계적 움직임",
                contractionType = "Eccentric (하강)",
                detailCategory = "주동근",
                description = null,
            ),
            ExerciseDetailInsert(
                exerciseId = exerciseId,
                movementType = "기계적 움직임",
                contractionType = "Eccentric (하강)",
                detailCategory = "길항근",
                description = null,
            ),
            // 기계적 움직임 - Concentric (상승)
            ExerciseDetailInsert(
                exerciseId = exerciseId,
                movementType = "기계적 움직임",
                contractionType = "Concentric (상승)",
                detailCategory = "Primary",
                description = null,
            ),
            ExerciseDetailInsert(
                exerciseId = exerciseId,
                movementType = "기계적 움직임",
                contractionType = "Concentric (상승)",
                detailCategory = "Secondary",
                description = null,
            ),
            ExerciseDetailInsert(
                exerciseId = exerciseId,
                movementType = "기계적 움직임",
                contractionType = "Concentric (상승)",
                detailCategory = "주동근",
                description = null,
            ),
            ExerciseDetailInsert(
                exerciseId = exerciseId,
                movementType = "기계적 움직임",
                contractionType = "Concentric (상승)",
                detailCategory = "길항근",
                description = null,
            ),
            // 기계적 움직임 - ROM 말단 고려
            ExerciseDetailInsert(
                exerciseId = exerciseId,
                movementType = "기계적 움직임",
                contractionType = "ROM 말단 고려",
                detailCategory = null,
                description = null,
            ),
            // 기계적 움직임 - 근육 분석
            ExerciseDetailInsert(
                exerciseId = exerciseId,
                movementType = "기계적 움직임",
                contractionType = "근육 분석",
                detailCategory = "주동근",
                description = null,
            ),
            ExerciseDetailInsert(
                exerciseId = exerciseId,
                movementType = "기계적 움직임",
                contractionType = "근육 분석",
                detailCategory = "길항근",
                description = null,
            ),
            // 안정화 기전 - NMC
            ExerciseDetailInsert(
                exerciseId = exerciseId,
                movementType = "안정화 기전",
                contractionType = "NMC",
                detailCategory = null,
                description = null,
            ),
            // 안정화 기전 - 능동 안정화
            ExerciseDetailInsert(
                exerciseId = exerciseId,
                movementType = "안정화 기전",
                contractionType = "능동 안정화",
                detailCategory = null,
                description = null,
            ),
            // 안정화 기전 - 특이성
            ExerciseDetailInsert(
                exerciseId = exerciseId,
                movementType = "안정화 기전",
                contractionType = "특이성",
                detailCategory = null,
                description = null,
            ),
        )
    }
}
