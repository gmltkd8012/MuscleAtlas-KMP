package com.rebuilding.muscleatlas.workout.usecase

import com.rebuilding.muscleatlas.data.model.ExerciseDetail

/**
 * ExerciseDetail 리스트를 movement_type, contraction_type 순서로 그룹핑하고 정렬하는 UseCase
 */
class ExerciseDetailGroupAndSortUseCase {

    companion object {
        // movementType 고정 순서 정의
        private val MOVEMENT_TYPE_ORDER = listOf(
            "기계적 움직임",
            "안정화 기전",
        )

        // contractionType 고정 순서 정의
        private val CONTRACTION_TYPE_ORDER = listOf(
            // 기계적 움직임
            "Eccentric",
            "Concentric",
            "ROM 말단 고려",

            // 안정화 기전
            "근신경 조절",
            "능동 안정화",
            "특이성",
        )

        // Detail Category 고정 순서
        private val DETAIL_CATEGORY_ORDER = listOf(
            "주요 움직임",
            "부가 움직임",
            "근위/원위",
            "주동근",
            "길항근",
        )
    }

    /**
     * ExerciseDetail 리스트를 그룹핑하고 정렬
     *
     * @param details 원본 ExerciseDetail 리스트
     * @return movementType -> contractionType -> ExerciseDetail 리스트로 이중 그룹핑된 Map
     */
    operator fun invoke(details: List<ExerciseDetail>): Map<String, Map<String, List<ExerciseDetail>>> {
        return details
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
                        // contractionType 내에서 detailCategory로 정렬
                        val sortedDetailList = detailList.sortedBy { detail ->
                            detail.detailCategory?.let { category ->
                                DETAIL_CATEGORY_ORDER.indexOfFirst { order -> category.contains(order) }
                                    .takeIf { it >= 0 } ?: Int.MAX_VALUE
                            } ?: Int.MAX_VALUE // null인 경우 맨 뒤로
                        }
                        innerAcc[contractionType] = sortedDetailList
                        innerAcc
                    }
                acc[movementType] = sortedContractionMap
                acc
            }
    }
}
