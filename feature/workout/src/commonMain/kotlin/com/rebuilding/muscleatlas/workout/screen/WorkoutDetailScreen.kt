package com.rebuilding.muscleatlas.workout.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Clear
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rebuilding.muscleatlas.data.model.ExerciseDetail
import com.rebuilding.muscleatlas.designsystem.component.BaseTextField
import com.rebuilding.muscleatlas.designsystem.component.PhotoBox
import com.rebuilding.muscleatlas.designsystem.component.rememberPhotoBoxState
import com.rebuilding.muscleatlas.workout.viewmodel.WorkoutDetailViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutDetailScreen(
    exerciseId: String,
    viewModel: WorkoutDetailViewModel = koinViewModel(),
    fromWorkoutScreen: Boolean,
    onNavigateBack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val colorScheme = MaterialTheme.colorScheme
    
    // BottomSheet 상태 관리
    var showEditSheet by remember { mutableStateOf(false) }
    var showSafetyEditSheet by remember { mutableStateOf(false) }
    var selectedTechnicalTitle by remember { mutableStateOf("") }
    var selectedTechnicalDetails by remember { mutableStateOf<List<ExerciseDetail>>(emptyList()) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val safetySheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(exerciseId) {
        viewModel.loadExerciseDetail(exerciseId)
    }

    Scaffold(
        containerColor = colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "운동 종목 상세",
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
                    IconButton(onClick = { /* TODO: Bookmark */ }) {
                        Icon(
                            imageVector = Icons.Default.BookmarkBorder,
                            contentDescription = "북마크",
                            tint = colorScheme.onBackground,
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
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    // Hero Image Section
                    item {
                        HeroImageSection(
                            exerciseId = exerciseId,
                            imageUrl = state.exercise?.exerciseImg,
                            isUploadingImage = state.isUploadingImage,
                            enabled = fromWorkoutScreen,
                            onImageSelected = { imageBytes ->
                                viewModel.uploadExerciseImage(exerciseId, imageBytes)
                            },
                            onImageDelete = {
                                state.exercise?.exerciseImg?.let { url ->
                                    viewModel.deleteExerciseImage(exerciseId, url)
                                }
                            }
                        )
                    }

                    // Error display
                    state.uploadError?.let { error ->
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = colorScheme.errorContainer
                                )
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = null,
                                        tint = colorScheme.error,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = error,
                                        color = colorScheme.onErrorContainer,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }
                    }

                    // Exercise Name
                    item {
                        Text(
                            text = state.exercise?.name ?: "운동",
                            color = colorScheme.onBackground,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }

                    // Tags
                    item {
                        ExerciseTagsRow()
                    }

                    // Movement Mechanics Section
                    state.groupedDetails["기계적 움직임"]?.let { contractionGroups ->
                        item {
                            SectionTitle(title = "MOVEMENT MECHANICS")
                        }

                        item {
                            MovementMechanicsCard(contractionGroups)
                        }
                    }

                    // Technical Breakdown Section (순서 유지하면서 필터링)
                    val technicalDetails = state.groupedDetails["기계적 움직임"]
                        ?.entries
                        ?.filter { it.key != "Eccentric" && it.key != "Concentric" && it.key != "근육 분석" }

                    if (!technicalDetails.isNullOrEmpty()) {
                        item {
                            SectionTitle(title = "TECHNICAL BREAKDOWN")
                        }

                        technicalDetails.forEach { (contractionType, details) ->
                            item {
                                TechnicalCard(
                                    title = contractionType,
                                    details = details,
                                    onClick = { isRom ->
                                        if (isRom) {
                                            selectedTechnicalTitle = contractionType
                                            selectedTechnicalDetails = details
                                            showSafetyEditSheet = true
                                        } else {
                                            selectedTechnicalTitle = contractionType
                                            selectedTechnicalDetails = details
                                            showEditSheet = true
                                        }
                                    },
                                )
                            }
                        }
                    }

                    // Stabilization & Safety Section
                    state.groupedDetails["안정화 기전"]?.let { stabilizationGroups ->
                        item {
                            SectionTitle(title = "STABILIZATION & SAFETY")
                        }

                        stabilizationGroups.forEach { (contractionType, details) ->
                            item {
                                SafetyCard(
                                    title = contractionType,
                                    details = details,
                                    onClick = {
                                        selectedTechnicalTitle = contractionType
                                        selectedTechnicalDetails = details
                                        showSafetyEditSheet = true
                                    },
                                )
                            }
                        }
                    }

                    // Bottom spacing
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
    
    // Technical Breakdown 편집 BottomSheet
    if (showEditSheet && fromWorkoutScreen) {
        ModalBottomSheet(
            onDismissRequest = { showEditSheet = false },
            sheetState = sheetState,
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
            modifier = Modifier.fillMaxWidth(),
        ) {
            TechnicalEditSheetContent(
                title = selectedTechnicalTitle,
                details = selectedTechnicalDetails,
                onSaveClick = { updatedDetails ->
                    viewModel.updateExerciseDetails(updatedDetails)
                    showEditSheet = false
                },
            )
        }
    }
    
    // Safety 편집 BottomSheet
    if (showSafetyEditSheet && fromWorkoutScreen) {
        ModalBottomSheet(
            onDismissRequest = { showSafetyEditSheet = false },
            sheetState = safetySheetState,
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
            modifier = Modifier.fillMaxWidth(),
        ) {
            SafetyEditSheetContent(
                title = selectedTechnicalTitle,
                details = selectedTechnicalDetails,
                onSaveClick = { updatedDetails ->
                    viewModel.updateExerciseDetails(updatedDetails)
                    showSafetyEditSheet = false
                },
            )
        }
    }
}

@Composable
private fun HeroImageSection(
    exerciseId: String,
    imageUrl: String?,
    isUploadingImage: Boolean,
    enabled: Boolean = true,
    onImageSelected: (ByteArray) -> Unit,
    onImageDelete: () -> Unit,
) {
    val colorScheme = MaterialTheme.colorScheme
    val photoBoxState = rememberPhotoBoxState(initialUrl = imageUrl)

    // Update state when imageUrl changes
    LaunchedEffect(imageUrl) {
        photoBoxState.setImageUrl(imageUrl)
    }

    // Update loading state
    LaunchedEffect(isUploadingImage) {
        photoBoxState.setLoading(isUploadingImage)
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        PhotoBox(
            state = photoBoxState,
            modifier = Modifier.fillMaxWidth(),
            size = 200.dp,
            shape = RoundedCornerShape(16.dp),
            placeholderColor = colorScheme.surface,
            borderColor = colorScheme.outline.copy(alpha = 0.3f),
            borderWidth = 1.dp,
            showAddIcon = false,
            enabled = enabled && !isUploadingImage,
            onImageSelected = onImageSelected,
        )

        // Placeholder when no image and not uploading
        if (!photoBoxState.hasImage && !isUploadingImage) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "🏋️",
                    fontSize = 64.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (enabled) "터치하여 이미지 추가" else "Exercise Image",
                    color = colorScheme.onSurfaceVariant,
                    fontSize = 14.sp,
                )
            }
        }

        // Delete button when image exists
        if (photoBoxState.hasImage && enabled && !isUploadingImage) {
            IconButton(
                onClick = onImageDelete,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(32.dp)
                    .background(
                        colorScheme.surface.copy(alpha = 0.9f),
                        RoundedCornerShape(16.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "이미지 삭제",
                    tint = colorScheme.error,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ExerciseTagsRow() {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ExerciseTag(text = "COMPOUND", isPrimary = true)
//        ExerciseTag(text = "Strength")
//        ExerciseTag(text = "Legs")
    }
}

@Composable
private fun ExerciseTag(
    text: String,
    isPrimary: Boolean = false,
) {
    val colorScheme = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (isPrimary) colorScheme.primary.copy(alpha = 0.2f)
                else colorScheme.surface,
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
    ) {
        Text(
            text = text,
            color = if (isPrimary) colorScheme.primary else colorScheme.onSurface,
            fontSize = 12.sp,
            fontWeight = if (isPrimary) FontWeight.Bold else FontWeight.Medium,
        )
    }
}

@Composable
private fun SectionTitle(title: String) {
    val colorScheme = MaterialTheme.colorScheme

    Text(
        text = title,
        color = colorScheme.onSurfaceVariant,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp,
        modifier = Modifier.padding(top = 8.dp),
    )
}

@Composable
private fun MovementMechanicsCard(
    contractionGroups: Map<String, List<ExerciseDetail>>,
) {
    val colorScheme = MaterialTheme.colorScheme

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // Phase Card (Eccentric/Concentric movement phases)
        val eccentricDetails = contractionGroups["Eccentric"] ?: emptyList()
        val concentricDetails = contractionGroups["Concentric"] ?: emptyList()

        PhaseCard(
            modifier = Modifier.weight(1f),
            title = "PHASE",
            iconColor = colorScheme.tertiary,
            items = listOf(
                PhaseItem(
                    label = "DESCENDING",
                    value = eccentricDetails.find { it.detailCategory == "Primary" }?.description
                        ?: "Flexion",
                ),
                PhaseItem(
                    label = "ASCENDING",
                    value = concentricDetails.find { it.detailCategory == "Primary" }?.description
                        ?: "Extension",
                ),
            ),
        )

        // Contraction Card
        PhaseCard(
            modifier = Modifier.weight(1f),
            title = "CONTRACTION",
            iconColor = colorScheme.primary,
            items = listOf(
                PhaseItem(label = "LOWERING", value = "Eccentric"),
                PhaseItem(label = "LIFTING", value = "Concentric"),
            ),
        )
    }
}

data class PhaseItem(
    val label: String,
    val value: String,
)

@Composable
private fun PhaseCard(
    modifier: Modifier = Modifier,
    title: String,
    iconColor: Color,
    items: List<PhaseItem>,
) {
    val colorScheme = MaterialTheme.colorScheme

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surface,
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "⇅",
                    color = iconColor,
                    fontSize = 16.sp,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    color = iconColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            items.forEach { item ->
                Text(
                    text = item.label,
                    color = colorScheme.onSurfaceVariant,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                )
                Text(
                    text = item.value,
                    color = colorScheme.onBackground,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun MuscleDetailRow(
    label: String,
    value: String,
    accentColor: Color,
) {
    val colorScheme = MaterialTheme.colorScheme

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .background(accentColor, RoundedCornerShape(2.dp)),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                color = accentColor,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp,
            )
        }
        Text(
            text = value,
            color = colorScheme.onBackground,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 12.dp, top = 4.dp),
        )
    }
}

@Composable
private fun TechnicalCard(
    title: String,
    details: List<ExerciseDetail>,
    onClick: (isRom: Boolean) -> Unit = {},
) {
    val colorScheme = MaterialTheme.colorScheme

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = { onClick(title.contains("ROM")) }
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surface,
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(
                            colorScheme.primary.copy(alpha = 0.2f),
                            RoundedCornerShape(4.dp)
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "📋",
                        fontSize = 12.sp,
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    color = colorScheme.primary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 고정된 순서로 정렬
            val sortedDetails = details.sortedBy { detail ->
                when (detail.detailCategory) {
                    "Primary" -> 0
                    "Secondary" -> 1
                    "근위/원위" -> 2
                    "주동근" -> 3
                    "길항근" -> 4
                    else -> 5
                }
            }

            sortedDetails.forEach { detail ->
                detail.detailCategory?.let { category ->
                    Text(
                        text = category,
                        color = colorScheme.primary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
                Text(
                    text = detail.description?.takeIf { it.isNotBlank() } ?: "내용을 입력해주세요.",
                    color = if (detail.description.isNullOrBlank()) {
                        colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    } else {
                        colorScheme.onSurface
                    },
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun SafetyCard(
    title: String,
    details: List<ExerciseDetail>,
    onClick: () -> Unit = {},
) {
    val colorScheme = MaterialTheme.colorScheme

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surface,
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val iconEmoji = when {
                    title.contains("ROM", ignoreCase = true) -> "⚠️"
                    title.contains("NMC", ignoreCase = true) -> "🔵"
                    else -> "ℹ️"
                }
                val iconColor = when {
                    title.contains("ROM", ignoreCase = true) -> colorScheme.tertiary
                    else -> colorScheme.secondary
                }

                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(iconColor.copy(alpha = 0.2f), RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = iconEmoji,
                        fontSize = 12.sp,
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = getDisplayTitle(title),
                    color = colorScheme.onBackground,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            details.forEach { detail ->
                Text(
                    text = detail.description?.takeIf { it.isNotBlank() } ?: "내용을 입력해주세요.",
                    color = if (detail.description.isNullOrBlank()) {
                        colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    } else {
                        colorScheme.onSurface
                    },
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

private fun getDisplayTitle(title: String): String {
    return when {
        title.contains("ROM", ignoreCase = true) -> "ROM End-Range Considerations"
        title.contains("NMC", ignoreCase = true) -> "Neuromuscular Control (NMC)"
        else -> title
    }
}

/**
 * Technical Breakdown 항목 편집용 BottomSheet 내용
 */
@Composable
private fun TechnicalEditSheetContent(
    title: String,
    details: List<ExerciseDetail>,
    onSaveClick: (List<ExerciseDetail>) -> Unit,
) {
    val colorScheme = MaterialTheme.colorScheme

    // 편집 가능한 상태들 (제목은 수정 불가)
    var primaryValue by remember { 
        mutableStateOf(details.find { it.detailCategory == "Primary" }?.description ?: "") 
    }
    var secondaryValue by remember { 
        mutableStateOf(details.find { it.detailCategory == "Secondary" }?.description ?: "") 
    }
    var proximalDistalValue by remember { 
        mutableStateOf(details.find { it.detailCategory == "근위/원위" }?.description ?: "") 
    }
    var agonistValue by remember { 
        mutableStateOf(details.find { it.detailCategory == "주동근" }?.description ?: "") 
    }
    var antagonistValue by remember { 
        mutableStateOf(details.find { it.detailCategory == "길항근" }?.description ?: "") 
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorScheme.surface)
            .padding(horizontal = 16.dp)
            .navigationBarsPadding()
            .padding(bottom = 16.dp),
    ) {
        // 헤더
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        colorScheme.primary.copy(alpha = 0.2f),
                        RoundedCornerShape(4.dp)
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "📋",
                    fontSize = 12.sp,
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                color = colorScheme.onBackground,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
        
        Spacer(modifier = Modifier.height(20.dp))

        // Primary 필드
        BaseTextField(
            value = primaryValue,
            labelText = "Primary",
            hintText = "Primary 내용을 입력하세요",
            onValueChanged = { primaryValue = it },
            onDelete = { primaryValue = "" }
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Secondary 필드
        BaseTextField(
            value = secondaryValue,
            labelText = "Secondary",
            hintText = "Secondary 내용을 입력하세요",
            onValueChanged = { secondaryValue = it },
            onDelete = { secondaryValue = "" }
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // 근위/원위 필드
        BaseTextField(
            value = proximalDistalValue,
            labelText = "근위/원위",
            hintText = "근위/원위 내용을 입력하세요",
            onValueChanged = { proximalDistalValue = it },
            onDelete = { proximalDistalValue = "" }
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // 주동근 필드
        BaseTextField(
            value = agonistValue,
            labelText = "주동근",
            hintText = "주동근 내용을 입력하세요",
            onValueChanged = { agonistValue = it },
            onDelete = { agonistValue = "" }
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // 길항근 필드
        BaseTextField(
            value = antagonistValue,
            labelText = "길항근",
            hintText = "길항근 내용을 입력하세요",
            onValueChanged = { antagonistValue = it },
            onDelete = { antagonistValue = "" }
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // 저장 버튼
        Button(
            onClick = {
                // 편집된 내용으로 새로운 details 리스트 생성
                val baseDetail = details.firstOrNull()
                val updatedDetails = buildList {
                    if (primaryValue.isNotBlank()) {
                        add(
                            ExerciseDetail(
                                id = details.find { it.detailCategory == "Primary" }?.id ?: "",
                                exerciseId = baseDetail?.exerciseId ?: "",
                                movementType = baseDetail?.movementType ?: "",
                                contractionType = baseDetail?.contractionType ?: "",
                                detailCategory = "Primary",
                                description = primaryValue,
                            )
                        )
                    }
                    if (secondaryValue.isNotBlank()) {
                        add(ExerciseDetail(
                            id = details.find { it.detailCategory == "Secondary" }?.id ?: "",
                            exerciseId = baseDetail?.exerciseId ?: "",
                            movementType = baseDetail?.movementType ?: "",
                            contractionType = baseDetail?.contractionType ?: "",
                            detailCategory = "Secondary",
                            description = secondaryValue,
                        ))
                    }
                    if (proximalDistalValue.isNotBlank()) {
                        add(ExerciseDetail(
                            id = details.find { it.detailCategory == "근위/원위" }?.id ?: "",
                            exerciseId = baseDetail?.exerciseId ?: "",
                            movementType = baseDetail?.movementType ?: "",
                            contractionType = baseDetail?.contractionType ?: "",
                            detailCategory = "근위/원위",
                            description = proximalDistalValue,
                        ))
                    }
                    if (agonistValue.isNotBlank()) {
                        add(ExerciseDetail(
                            id = details.find { it.detailCategory == "주동근" }?.id ?: "",
                            exerciseId = baseDetail?.exerciseId ?: "",
                            movementType = baseDetail?.movementType ?: "",
                            contractionType = baseDetail?.contractionType ?: "",
                            detailCategory = "주동근",
                            description = agonistValue,
                        ))
                    }
                    if (antagonistValue.isNotBlank()) {
                        add(ExerciseDetail(
                            id = details.find { it.detailCategory == "길항근" }?.id ?: "",
                            exerciseId = baseDetail?.exerciseId ?: "",
                            movementType = baseDetail?.movementType ?: "",
                            contractionType = baseDetail?.contractionType ?: "",
                            detailCategory = "길항근",
                            description = antagonistValue,
                        ))
                    }
                }
                onSaveClick(updatedDetails)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.primary,
            ),
        ) {
            Text(
                text = "저장",
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

/**
 * Safety 항목 편집용 BottomSheet 내용 (제목, description만 수정)
 */
@Composable
private fun SafetyEditSheetContent(
    title: String,
    details: List<ExerciseDetail>,
    onSaveClick: (List<ExerciseDetail>) -> Unit,
) {
    val colorScheme = MaterialTheme.colorScheme

    // 편집 가능한 상태들 (제목은 수정 불가)
    var descriptionValue by remember {
        mutableStateOf(details.firstOrNull()?.description ?: "")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorScheme.surface)
            .padding(horizontal = 16.dp)
            .navigationBarsPadding()
            .padding(bottom = 16.dp),
    ) {
        // 헤더
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val iconEmoji = when {
                title.contains("ROM", ignoreCase = true) -> "⚠️"
                title.contains("NMC", ignoreCase = true) -> "🔵"
                else -> "ℹ️"
            }
            
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        colorScheme.primary.copy(alpha = 0.2f),
                        RoundedCornerShape(4.dp)
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = iconEmoji,
                    fontSize = 12.sp,
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                color = colorScheme.onBackground,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
        
        Spacer(modifier = Modifier.height(20.dp))

        // Description 필드
        BaseTextField(
            value = descriptionValue,
            labelText = "설명",
            hintText = "내용을 입력하세요",
            singleLine = false,
            onValueChanged = { descriptionValue = it },
            onDelete = { descriptionValue = "" }
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // 저장 버튼
        Button(
            onClick = {
                val baseDetail = details.firstOrNull()
                val updatedDetails = if (descriptionValue.isNotBlank() && baseDetail != null) {
                    listOf(
                        ExerciseDetail(
                            id = baseDetail.id,
                            exerciseId = baseDetail.exerciseId,
                            movementType = baseDetail.movementType,
                            contractionType = baseDetail.contractionType,
                            detailCategory = baseDetail.detailCategory,
                            description = descriptionValue,
                        )
                    )
                } else {
                    emptyList()
                }
                onSaveClick(updatedDetails)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.primary,
            ),
        ) {
            Text(
                text = "저장",
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}
