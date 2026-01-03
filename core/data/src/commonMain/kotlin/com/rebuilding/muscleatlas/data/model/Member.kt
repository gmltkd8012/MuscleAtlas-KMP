package com.rebuilding.muscleatlas.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Supabase members 테이블 매핑 모델
 *
 * @property id 고유 기본키
 * @property userId 소유자 계정 ID (auth.users 참조)
 * @property imgUrl 프로필 사진 URL
 * @property name 회원명
 * @property memo 부가 정보
 * @property createdAt 생성 시간
 * @property updatedAt 수정 시간 (메모 업데이트 시 갱신, Unix timestamp 밀리초)
 */
@Serializable
data class Member(
    @SerialName("id") val id: String,
    @SerialName("user_id") val userId: String? = null,
    @SerialName("img_url") val imgUrl: String? = null,
    @SerialName("name") val name: String,
    @SerialName("memo") val memo: String,
    @SerialName("created_at") val createdAt: Long,
    @SerialName("updated_at") val updatedAt: Long? = null,
)

/**
 * 회원 생성 요청용 DTO
 * currentMills는 Supabase DEFAULT로 자동 생성
 */
@Serializable
data class CreateMemberRequest(
    @SerialName("img_url") val imgUrl: String? = null,
    @SerialName("name") val name: String,
    @SerialName("memo") val memo: String,
    @SerialName("user_id") val userId : String,
)

/**
 * 회원 수정 요청용 DTO (모든 필드 nullable - 부분 업데이트 지원)
 */
@Serializable
data class UpdateMemberRequest(
    @SerialName("img_url") val imgUrl: String? = null,
    val name: String? = null,
    val memo: String? = null,
    @SerialName("updated_at") val updatedAt: Long? = null,
)

/**
 * Supabase member_exercises 테이블 매핑 모델
 *
 * @property id 고유 기본키
 * @property memberId 회원 ID (member 테이블 참조)
 * @property exerciseId 운동 종목 ID (exercises 테이블 참조)
 * @property canPerform 동작 가능 여부
 * @property createdAt 생성 시간
 */
@Serializable
data class MemberExercise(
    val id: String,
    @SerialName("member_id")
    val memberId: String,
    @SerialName("exercise_id")
    val exerciseId: String,
    @SerialName("can_perform")
    val canPerform: Boolean = false,
    @SerialName("created_at")
    val createdAt: String? = null,
)

/**
 * 회원-운동 생성 요청용 DTO (id는 Supabase에서 자동 생성)
 */
@Serializable
data class MemberExerciseInsert(
    @SerialName("member_id")
    val memberId: String,
    @SerialName("exercise_id")
    val exerciseId: String,
    @SerialName("can_perform")
    val canPerform: Boolean = false,
)

/**
 * 회원-운동 수정 요청용 DTO
 */
@Serializable
data class MemberExerciseUpdate(
    @SerialName("can_perform")
    val canPerform: Boolean,
)
