package com.rebuilding.muscleatlas.member.viewmodel

import com.rebuilding.muscleatlas.data.model.Exercise
import com.rebuilding.muscleatlas.data.model.Member
import com.rebuilding.muscleatlas.data.model.MemberExercise
import com.rebuilding.muscleatlas.data.model.MemberExerciseUpdate
import com.rebuilding.muscleatlas.data.model.MemberInvite
import com.rebuilding.muscleatlas.data.model.MemberTagData
import com.rebuilding.muscleatlas.data.model.UpdateMemberRequest
import com.rebuilding.muscleatlas.data.repository.ExerciseRepository
import com.rebuilding.muscleatlas.data.repository.MemberExerciseRepository
import com.rebuilding.muscleatlas.data.repository.MemberInviteRepository
import com.rebuilding.muscleatlas.data.repository.MemberRepository
import com.rebuilding.muscleatlas.ui.base.StateViewModel
import com.rebuilding.muscleatlas.util.DateFormatter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MemberDetailViewModel(
    private val memberRepository: MemberRepository,
    private val memberExerciseRepository: MemberExerciseRepository,
    private val exerciseRepository: ExerciseRepository,
    private val memberInviteRepository: MemberInviteRepository,
) : StateViewModel<MemberDetailState, MemberDetailSideEffect>(MemberDetailState()) {

    fun loadMemberDetail(memberId: String) {
        launch {
            reduceState { copy(isLoading = true) }
            
            try {
                // 1. 회원 정보 로드
                val member = memberRepository.getMember(memberId)
                
                // 2. 회원의 운동 목록 로드
                val memberExercises = memberExerciseRepository.getMemberExercises(memberId).first()
                
                // 3. 모든 운동 정보 로드 (이름을 가져오기 위해)
                val exercises = exerciseRepository.getExercises().first()
                val exerciseMap = exercises.associateBy { it.id }
                
                // 4. MemberExercise + Exercise 정보 결합
                val exerciseItems = memberExercises.mapNotNull { memberExercise ->
                    exerciseMap[memberExercise.exerciseId]?.let { exercise ->
                        MemberExerciseItem(
                            memberExercise = memberExercise,
                            exercise = exercise,
                        )
                    }
                }
                
                // 5. DB에서 가져온 태그를 UI용 MemberTag로 변환
                val tags = member?.tags?.mapIndexed { index, tagData ->
                    tagData.toMemberTag(index)
                } ?: emptyList()
                
                reduceState {
                    copy(
                        isLoading = false,
                        member = member,
                        exerciseItems = exerciseItems,
                        tags = tags,
                        error = null,
                    )
                }
            } catch (e: Exception) {
                reduceState {
                    copy(
                        isLoading = false,
                        error = e.message,
                    )
                }
            }
        }
    }
    
    fun updateExerciseCanPerform(memberExerciseId: String, canPerform: Boolean) {
        launch {
            try {
                memberExerciseRepository.updateMemberExercise(
                    id = memberExerciseId,
                    update = MemberExerciseUpdate(canPerform = canPerform),
                )
                
                // 로컬 상태 업데이트
                reduceState {
                    copy(
                        exerciseItems = exerciseItems.map { item ->
                            if (item.memberExercise.id == memberExerciseId) {
                                item.copy(
                                    memberExercise = item.memberExercise.copy(canPerform = canPerform)
                                )
                            } else {
                                item
                            }
                        }
                    )
                }
            } catch (e: Exception) {
                reduceState { copy(error = e.message) }
            }
        }
    }
    
    /**
     * 회원 메모 업데이트
     */
    fun updateMemo(memberId: String, memberName: String, newMemo: String) {
        launch {
            try {
                val currentTimeMillis = DateFormatter.getCurrentTimeMillis()
                
                val updatedMember = memberRepository.updateMember(
                    id = memberId,
                    request = UpdateMemberRequest(
                        name = memberName,
                        memo = newMemo,
                        updatedAt = currentTimeMillis,
                    )
                )
                
                // 로컬 상태 업데이트
                reduceState {
                    copy(member = updatedMember)
                }
            } catch (e: Exception) {
                reduceState { copy(error = e.message) }
            }
        }
    }
    
    /**
     * 태그 추가
     */
    fun addTag(text: String, icon: String, colorType: TagColorType) {
        launch {
            try {
                val memberId = state.value.member?.id ?: return@launch
                
                val newTag = MemberTag(
                    id = generateTagId(),
                    text = text,
                    icon = icon,
                    colorType = colorType,
                )
                
                // 로컬 상태 먼저 업데이트 (빠른 UI 반응)
                val updatedTags = state.value.tags + newTag
                reduceState { copy(tags = updatedTags) }
                
                // DB에 저장
                val tagsForDb = updatedTags.map { it.toMemberTagData() }
                val updatedMember = memberRepository.updateMemberTags(memberId, tagsForDb)
                
                // 서버 응답으로 상태 동기화
                reduceState { copy(member = updatedMember) }
            } catch (e: Exception) {
                reduceState { copy(error = e.message) }
            }
        }
    }
    
    /**
     * 태그 삭제
     */
    fun removeTag(tagId: String) {
        launch {
            try {
                val memberId = state.value.member?.id ?: return@launch
                
                // 로컬 상태 먼저 업데이트 (빠른 UI 반응)
                val updatedTags = state.value.tags.filter { it.id != tagId }
                reduceState { copy(tags = updatedTags) }
                
                // DB에 저장
                val tagsForDb = updatedTags.map { it.toMemberTagData() }
                val updatedMember = memberRepository.updateMemberTags(memberId, tagsForDb)
                
                // 서버 응답으로 상태 동기화
                reduceState { copy(member = updatedMember) }
            } catch (e: Exception) {
                reduceState { copy(error = e.message) }
            }
        }
    }
    
    /**
     * 공유용 초대 코드 생성
     */
    fun createShareInvite() {
        launch {
            try {
                val memberId = state.value.member?.id ?: return@launch
                
                reduceState { copy(isCreatingInvite = true) }
                
                // 초대 코드 생성 또는 기존 유효 코드 반환
                val invite = memberInviteRepository.getOrCreateInvite(memberId)
                
                reduceState { 
                    copy(
                        isCreatingInvite = false,
                        currentInvite = invite,
                    ) 
                }
                
                // SideEffect로 공유 UI 트리거
                sendSideEffect(MemberDetailSideEffect.ShareInvite(invite))
            } catch (e: Exception) {
                reduceState { 
                    copy(
                        isCreatingInvite = false,
                        error = e.message,
                    ) 
                }
            }
        }
    }
    
    /**
     * 공유 링크 URL 생성
     */
    fun getShareUrl(inviteCode: String): String {
        // TODO: 실제 Edge Function URL로 교체
        return "https://onpadytjzizosabsrsyp.supabase.co/functions/v1/member-invite?code=$inviteCode"
    }
    
    private fun generateTagId(): String {
        return "tag_${DateFormatter.getCurrentTimeMillis()}"
    }
}

/**
 * 회원 운동 항목 (MemberExercise + Exercise 정보 결합)
 */
data class MemberExerciseItem(
    val memberExercise: MemberExercise,
    val exercise: Exercise,
)

/**
 * 회원 태그 (UI용)
 */
data class MemberTag(
    val id: String,
    val text: String,
    val icon: String,
    val colorType: TagColorType,
) {
    /**
     * DB 저장용 MemberTagData로 변환
     */
    fun toMemberTagData(): MemberTagData = MemberTagData(
        text = text,
        icon = icon,
        color = colorType.name,
    )
}

/**
 * 태그 색상 타입
 */
enum class TagColorType {
    PRIMARY,   // 기본 (파란색 계열)
    WARNING,   // 주의 (빨간색 계열)
    SUCCESS;   // 성공/긍정 (초록색 계열)
    
    companion object {
        fun fromString(value: String): TagColorType {
            return entries.find { it.name == value } ?: PRIMARY
        }
    }
}

/**
 * DB에서 가져온 MemberTagData를 UI용 MemberTag로 변환
 */
fun MemberTagData.toMemberTag(index: Int): MemberTag = MemberTag(
    id = "tag_$index",
    text = text,
    icon = icon,
    colorType = TagColorType.fromString(color),
)

data class MemberDetailState(
    val isLoading: Boolean = false,
    val member: Member? = null,
    val exerciseItems: List<MemberExerciseItem> = emptyList(),
    val tags: List<MemberTag> = emptyList(),
    val isCreatingInvite: Boolean = false,
    val currentInvite: MemberInvite? = null,
    val error: String? = null,
)

sealed interface MemberDetailSideEffect {
    data class ShareInvite(val invite: MemberInvite) : MemberDetailSideEffect
}
