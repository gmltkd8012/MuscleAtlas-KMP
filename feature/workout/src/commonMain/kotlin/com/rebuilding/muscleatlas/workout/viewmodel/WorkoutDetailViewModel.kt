package com.rebuilding.muscleatlas.workout.viewmodel

import com.rebuilding.muscleatlas.data.model.Exercise
import com.rebuilding.muscleatlas.data.model.ExerciseDetail
import com.rebuilding.muscleatlas.data.repository.ExerciseRepository
import com.rebuilding.muscleatlas.data.repository.StorageRepository
import com.rebuilding.muscleatlas.ui.base.StateViewModel
import com.rebuilding.muscleatlas.ui.util.Logger
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class WorkoutDetailViewModel(
    private val exerciseRepository: ExerciseRepository,
    private val storageRepository: StorageRepository,
) : StateViewModel<WorkoutDetailState, WorkoutDetailSideEffect>(WorkoutDetailState()) {

    private var currentExerciseId: String? = null

    companion object {
        private const val TAG = "WorkoutDetailViewModel"

        // contractionType 고정 순서 정의
        private val CONTRACTION_TYPE_ORDER = listOf(
            // 기계적 움직임
            "Eccentric",
            "Concentric",
            "ROM 말단 고려",

            // 안정화 기전
            "NMC",
            "능동 안정화",
            "특이성",
        )

        // movementType 고정 순서 정의
        private val MOVEMENT_TYPE_ORDER = listOf(
            "기계적 움직임",
            "안정화 기전",
        )
    }

    fun loadExerciseDetail(exerciseId: String) {
        currentExerciseId = exerciseId
        launch {
            // 운동 정보 조회
            val exercise = exerciseRepository.getExercise(exerciseId)
            reduceState { copy(exercise = exercise) }

            // 상세 정보 조회
            exerciseRepository.getExerciseDetails(exerciseId)
                .onStart {
                    reduceState { copy(isLoading = true) }
                }
                .catch { e ->
                    Logger.e(TAG, "운동 상세 정보 로드 실패", e)
                    reduceState { copy(isLoading = false) }
                }
                .collect { details ->
                    // movement_type, contraction_type 순서로 그룹핑 (고정 순서 적용, LinkedHashMap으로 순서 유지)
                    val groupedDetails = details
                        .groupBy { it.movementType }
                        .entries
                        .sortedBy { (key, _) ->
                            MOVEMENT_TYPE_ORDER.indexOfFirst { order -> key.contains(order) }
                                .takeIf { it >= 0 } ?: Int.MAX_VALUE
                        }
                        .fold(linkedMapOf<String, Map<String, List<ExerciseDetail>>>()) { acc, (movementType, items) ->
                            val sortedContractionMap = items
                                .groupBy { it.contractionType }
                                .entries
                                .sortedBy { (key, _) ->
                                    // key가 ORDER 항목을 포함하는지 체크 (예: "Eccentric (내림)"이 "Eccentric" 포함)
                                    CONTRACTION_TYPE_ORDER.indexOfFirst { order -> key.contains(order) }
                                        .takeIf { it >= 0 } ?: Int.MAX_VALUE
                                }
                                .fold(linkedMapOf<String, List<ExerciseDetail>>()) { innerAcc, (contractionType, detailList) ->
                                    innerAcc[contractionType] = detailList
                                    innerAcc
                                }
                            acc[movementType] = sortedContractionMap
                            acc
                        }
                    reduceState {
                        copy(
                            isLoading = false,
                            details = details,
                            groupedDetails = groupedDetails,
                        )
                    }
                }
        }
    }

    /**
     * 운동 이미지 업로드 및 URL 업데이트
     */
    fun uploadExerciseImage(exerciseId: String, imageBytes: ByteArray) {
        launch {
            try {
                reduceState { copy(isUploadingImage = true, uploadError = null) }

                // 1. Upload to Supabase Storage
                val imageUrl = storageRepository.uploadExerciseImage(exerciseId, imageBytes)

                // 2. Update database with image URL
                val updatedExercise = exerciseRepository.updateExerciseImageUrl(exerciseId, imageUrl)

                // 3. Update UI state
                reduceState {
                    copy(
                        exercise = updatedExercise,
                        isUploadingImage = false
                    )
                }
            } catch (e: Exception) {
                Logger.e(TAG, "Exercise image upload failed", e)
                reduceState {
                    copy(
                        isUploadingImage = false,
                        uploadError = "이미지 업로드에 실패했습니다"
                    )
                }
            }
        }
    }

    /**
     * 운동 이미지 삭제
     */
    fun deleteExerciseImage(exerciseId: String, imageUrl: String) {
        launch {
            try {
                reduceState { copy(isUploadingImage = true, uploadError = null) }

                // 1. Delete from storage
                storageRepository.deleteExerciseImage(imageUrl)

                // 2. Remove URL from database
                val updatedExercise = exerciseRepository.updateExerciseImageUrl(exerciseId, null)

                // 3. Update UI state
                reduceState {
                    copy(
                        exercise = updatedExercise,
                        isUploadingImage = false
                    )
                }
            } catch (e: Exception) {
                Logger.e(TAG, "Exercise image deletion failed", e)
                reduceState {
                    copy(
                        isUploadingImage = false,
                        uploadError = "이미지 삭제에 실패했습니다"
                    )
                }
            }
        }
    }

    /**
     * 운동 상세 정보 업데이트
     */
    fun updateExerciseDetails(details: List<ExerciseDetail>) {
        launch {
            try {
                reduceState { copy(isLoading = true) }
                exerciseRepository.updateExerciseDetails(details)
                // 업데이트 후 데이터 다시 조회
                currentExerciseId?.let { loadExerciseDetail(it) }
            } catch (e: Exception) {
                Logger.e(TAG, "운동 상세 정보 업데이트 실패", e)
                reduceState { copy(isLoading = false) }
            }
        }
    }
}

data class WorkoutDetailState(
    val isLoading: Boolean = false,
    val isUploadingImage: Boolean = false,
    val uploadError: String? = null,
    val exercise: Exercise? = null,
    val details: List<ExerciseDetail> = emptyList(),
    val groupedDetails: Map<String, Map<String, List<ExerciseDetail>>> = emptyMap(),
)

sealed interface WorkoutDetailSideEffect
