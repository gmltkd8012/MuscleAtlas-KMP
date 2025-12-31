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
 * @property currentMills 저장 시간 (Unix timestamp)
 */
@Serializable
data class Member(
    val id: String,
    @SerialName("user_id")
    val userId: String? = null,
    @SerialName("img_url")
    val imgUrl: String? = null,
    val name: String,
    val memo: String,
    @SerialName("current_mills")
    val currentMills: Long,
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
 * 회원 수정 요청용 DTO
 */
@Serializable
data class UpdateMemberRequest(
    @SerialName("img_url")
    val imgUrl: String? = null,
    val name: String? = null,
    val memo: String? = null,
)
