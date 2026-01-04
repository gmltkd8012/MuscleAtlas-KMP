package com.rebuilding.muscleatlas.member.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rebuilding.muscleatlas.member.viewmodel.MemberDetailViewModel
import com.rebuilding.muscleatlas.member.viewmodel.MemberExerciseItem
import com.rebuilding.muscleatlas.ui.util.Logger
import com.rebuilding.muscleatlas.util.DateFormatter
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemberDetailScreen(
    memberId: String,
    viewModel: MemberDetailViewModel = koinViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToWorkoutDetail: (exerciseId: String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val colorScheme = MaterialTheme.colorScheme
    
    // 메모 편집 BottomSheet 상태
    var showMemoEditSheet by remember { mutableStateOf(false) }
    val memoSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(memberId) {
        viewModel.loadMemberDetail(memberId)
    }

    Scaffold(
        containerColor = colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "회원 상세",
                        color = colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "뒤로가기",
                            tint = colorScheme.onBackground,
                        )
                    }
                },
                actions = {
                    TextButton(onClick = { /* TODO: 편집 */ }) {
                        Text(
                            text = "공유",
                            color = colorScheme.primary,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorScheme.background,
                ),
            )
        },
    ) { innerPadding ->
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(color = colorScheme.primary)
                }
            }

            state.error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "오류: ${state.error}",
                        color = colorScheme.error,
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                ) {
                    // 프로필 섹션
                    item {
                        ProfileSection(
                            name = state.member?.name ?: "",
                            memo = state.member?.memo ?: "",
                        )
                    }

                    // 최근 메모 카드 (클릭하면 편집)
                    item {
                        val formattedDate = state.member?.updatedAt?.let {
                            DateFormatter.formatMillisToKoreanDate(it)
                        } ?: state.member?.createdAt.let {
                            DateFormatter.formatMillisToKoreanDate(it, "최초 등록")
                        }

                        MemoCard(
                            memo = state.member?.memo ?: "",
                            formattedDate = formattedDate,
                            onClick = { showMemoEditSheet = true },
                        )
                    }

                    // 운동 목록 헤더
                    item {
                        Text(
                            text = "운동 종목",
                            color = colorScheme.onBackground,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        )
                    }

                    // 운동 목록
                    items(
                        items = state.exerciseItems,
                        key = { it.memberExercise.id }
                    ) { item ->
                        ExerciseListItem(
                            item = item,
                            onItemClick = { onNavigateToWorkoutDetail(item.exercise.id) },
                            onCanPerformChange = { canPerform ->
                                viewModel.updateExerciseCanPerform(
                                    memberExerciseId = item.memberExercise.id,
                                    canPerform = canPerform,
                                )
                            },
                        )
                    }

                    // 하단 여백
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
    
    // 메모 편집 BottomSheet
    if (showMemoEditSheet) {
        ModalBottomSheet(
            onDismissRequest = { showMemoEditSheet = false },
            sheetState = memoSheetState,
            containerColor = colorScheme.surface,
            dragHandle = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colorScheme.surface)
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Box(
                        modifier = Modifier
                            .width(32.dp)
                            .height(4.dp)
                            .background(
                                colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                                RoundedCornerShape(2.dp)
                            ),
                    )
                }
            },
        ) {
            MemoEditSheetContent(
                currentMemo = state.member?.memo ?: "",
                onSaveClick = { newMemo ->
                    val memberName = state.member?.name
                    memberName?.let {
                        viewModel.updateMemo(memberId, memberName, newMemo)
                    }

                    showMemoEditSheet = false
                },
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ProfileSection(
    name: String,
    memo: String,
) {
    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // 프로필 이미지
        Box(
            modifier = Modifier.size(120.dp),
            contentAlignment = Alignment.BottomEnd,
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = name.firstOrNull()?.toString() ?: "?",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onSurfaceVariant,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 이름
        Text(
            text = name,
            color = colorScheme.onBackground,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 태그들
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            StatusTag(
                text = "PT 10회 남음",
                icon = "✨",
                backgroundColor = colorScheme.primary.copy(alpha = 0.2f),
                textColor = colorScheme.primary,
            )
            StatusTag(
                text = "허리 주의",
                icon = "⚠️",
                backgroundColor = colorScheme.error.copy(alpha = 0.2f),
                textColor = colorScheme.error,
            )
        }
    }
}

@Composable
private fun StatusTag(
    text: String,
    icon: String,
    backgroundColor: Color,
    textColor: Color,
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = icon,
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = text,
                color = textColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Composable
private fun MemoCard(
    memo: String,
    formattedDate: String,
    onClick: () -> Unit,
) {
    val colorScheme = MaterialTheme.colorScheme

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surface,
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "최근 메모",
                    color = colorScheme.onSurfaceVariant,
                    fontSize = 14.sp,
                )
                Text(
                    text = formattedDate,
                    color = colorScheme.onSurfaceVariant,
                    fontSize = 14.sp,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = memo.ifEmpty { "메모가 없습니다. 탭하여 추가하세요." },
                color = if (memo.isEmpty()) colorScheme.onSurfaceVariant else colorScheme.onSurface,
                fontSize = 15.sp,
                lineHeight = 22.sp,
            )
        }
    }
}

/**
 * 메모 편집 BottomSheet 내용
 */
@Composable
private fun MemoEditSheetContent(
    currentMemo: String,
    onSaveClick: (String) -> Unit,
) {
    val colorScheme = MaterialTheme.colorScheme
    var memoText by remember { mutableStateOf(currentMemo) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorScheme.surface)
            .padding(horizontal = 16.dp)
            .navigationBarsPadding()
            .padding(bottom = 16.dp),
    ) {
        Text(
            text = "메모 편집",
            color = colorScheme.onSurface,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = memoText,
            onValueChange = { memoText = it },
            label = { Text("메모") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 4,
            maxLines = 8,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onSaveClick(memoText) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.primary,
                contentColor = colorScheme.onPrimary,
            ),
        ) {
            Text(
                text = "저장",
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Composable
private fun ExerciseListItem(
    item: MemberExerciseItem,
    onItemClick: () -> Unit,
    onCanPerformChange: (Boolean) -> Unit,
) {
    val colorScheme = MaterialTheme.colorScheme
    
    // 운동별 색상 및 아이콘
    val (iconColor, iconEmoji) = getExerciseIconAndColor(item.exercise.name)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable(onClick = onItemClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surface,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // 아이콘
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(iconColor.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = iconEmoji,
                    fontSize = 24.sp,
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // 운동 정보
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = item.exercise.name,
                    color = colorScheme.onSurface,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (item.memberExercise.canPerform) "수행 가능" else "수행 불가",
                    color = if (item.memberExercise.canPerform) {
                        Color(0xFF4CAF50)
                    } else {
                        colorScheme.onSurfaceVariant
                    },
                    fontSize = 13.sp,
                )
            }

            // 수행 가능 여부 스위치
            Switch(
                checked = item.memberExercise.canPerform,
                onCheckedChange = onCanPerformChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = colorScheme.onPrimary,
                    checkedTrackColor = colorScheme.primary,
                    uncheckedThumbColor = colorScheme.onSurfaceVariant,
                    uncheckedTrackColor = colorScheme.surfaceVariant,
                ),
            )

            Spacer(modifier = Modifier.width(8.dp))

            // 화살표
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = colorScheme.onSurfaceVariant,
            )
        }
    }
}

/**
 * 운동 이름에 따른 아이콘과 색상 반환
 */
private fun getExerciseIconAndColor(exerciseName: String): Pair<Color, String> {
    return when {
        exerciseName.contains("벤치") || exerciseName.contains("프레스") -> 
            Color(0xFF5C6BC0) to "💪"
        exerciseName.contains("데드") -> 
            Color(0xFF8D6E63) to "🏋️"
        exerciseName.contains("스쿼트") -> 
            Color(0xFFFF9800) to "🦵"
        exerciseName.contains("플랭크") || exerciseName.contains("코어") -> 
            Color(0xFF607D8B) to "⏱️"
        exerciseName.contains("풀업") || exerciseName.contains("턱걸이") -> 
            Color(0xFF009688) to "💪"
        exerciseName.contains("런지") -> 
            Color(0xFFE91E63) to "🦿"
        else -> 
            Color(0xFF9E9E9E) to "🏃"
    }
}

