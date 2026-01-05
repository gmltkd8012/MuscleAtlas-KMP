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
 * @property tags 회원 태그 목록 (JSONB)
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
    @SerialName("tags") val tags: List<MemberTagData>? = null,
    @SerialName("created_at") val createdAt: Long,
    @SerialName("updated_at") val updatedAt: Long? = null,
)

/**
 * 회원 태그 데이터 (JSONB 저장용)
 *
 * @property text 태그 텍스트
 * @property icon 태그 아이콘 (이모지)
 * @property color 태그 색상 타입 ("PRIMARY", "WARNING", "SUCCESS")
 */
@Serializable
data class MemberTagData(
    val text: String,
    val icon: String,
    val color: String,
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
    val tags: List<MemberTagData>? = null,
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

/**
 * Supabase member_invite 테이블 매핑 모델
 *
 * @property id 고유 기본키 (invite-UUID 형태)
 * @property memberId 회원 ID
 * @property inviteCode 초대 코드 (8자리)
 * @property createdBy 생성한 트레이너 ID
 * @property expiresAt 만료 시간 (밀리초)
 * @property createdAt 생성 시간 (밀리초)
 */
@Serializable
data class MemberInvite(
    val id: String,
    @SerialName("member_id")
    val memberId: String,
    @SerialName("invite_code")
    val inviteCode: String,
    @SerialName("created_by")
    val createdBy: String,
    @SerialName("expires_at")
    val expiresAt: Long,
    @SerialName("created_at")
    val createdAt: Long,
)

/**
 * 회원 초대 생성 요청용 DTO
 * id, expires_at, created_at은 Supabase DEFAULT로 자동 생성
 */
@Serializable
data class CreateMemberInviteRequest(
    @SerialName("member_id")
    val memberId: String,
    @SerialName("invite_code")
    val inviteCode: String,
    @SerialName("created_by")
    val createdBy: String,
)
