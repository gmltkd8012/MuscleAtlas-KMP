package com.rebuilding.muscleatlas.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Supabase exercises 테이블 매핑 모델
 *
 * @property id 운동 종목 고유 ID
 * @property name 운동 종목명 (스쿼트, 데드리프트 등)
 * @property createdAt 생성 시간
 */
@Serializable
data class Exercise(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("exercise_img") val exerciseImg: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
)

/**
 * 운동 추가용 DTO (id는 Supabase에서 자동 생성)
 */
@Serializable
data class ExerciseInsert(
    @SerialName("name") val name: String,
    @SerialName("exercise_img") val exerciseImg: String? = null,
)

/**
 * 운동 상세 정보 추가용 DTO (id는 Supabase에서 자동 생성)
 */
@Serializable
data class ExerciseDetailInsert(
    @SerialName("exercise_id")
    val exerciseId: String,
    @SerialName("movement_type")
    val movementType: String,
    @SerialName("contraction_type")
    val contractionType: String,
    @SerialName("detail_category")
    val detailCategory: String? = null,
    val description: String? = null,
)

/**
 * Supabase exercise_details 테이블 매핑 모델
 *
 * @property id 상세 정보 고유 ID
 * @property exerciseId 운동 종목 ID (exercises 테이블 참조)
 * @property movementType 움직임 유형 (기계적 움직임, 안정화 기전)
 * @property contractionType 수축 유형 (Eccentric, Concentric, ROM 말단 고려, NMC 등)
 * @property detailCategory 세부 카테고리 (Primary, Secondary, 주동근, 길항근 등)
 * @property description 설명
 * @property createdAt 생성 시간
 */
@Serializable
data class ExerciseDetail(
    val id: String,
    @SerialName("exercise_id")
    val exerciseId: String,
    @SerialName("movement_type")
    val movementType: String,
    @SerialName("contraction_type")
    val contractionType: String,
    @SerialName("detail_category")
    val detailCategory: String? = null,
    val description: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
)
