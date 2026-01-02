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
import com.rebuilding.muscleatlas.workout.viewmodel.WorkoutDetailViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutDetailScreen(
    exerciseId: String,
    viewModel: WorkoutDetailViewModel = koinViewModel(),
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
        bottomBar = {
            // Add to Routine Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorScheme.background)
                    .padding(16.dp),
            ) {
                Button(
                    onClick = { /* TODO: Add to routine */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(26.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.primary,
                    ),
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = colorScheme.onPrimary,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Add to Routine",
                        color = colorScheme.onPrimary,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                    )
                }
            }
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
                        .padding(innerPadding)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "오류: ${state.error}",
                        color = colorScheme.onBackground,
                    )
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
                        HeroImageSection()
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

                    // Muscle Analysis Section
                    state.groupedDetails["기계적 움직임"]?.get("근육 분석")?.let { muscleDetails ->
                        item {
                            MuscleAnalysisCard(muscleDetails)
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
    if (showEditSheet) {
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
    if (showSafetyEditSheet) {
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
private fun HeroImageSection() {
    val colorScheme = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        colorScheme.surface,
                        colorScheme.background,
                    ),
                ),
            ),
        contentAlignment = Alignment.Center,
    ) {
        // Placeholder for exercise image
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
                text = "Exercise Image",
                color = colorScheme.onSurfaceVariant,
                fontSize = 14.sp,
            )
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
private fun MuscleAnalysisCard(muscleDetails: List<ExerciseDetail>) {
    val colorScheme = MaterialTheme.colorScheme

    Card(
        modifier = Modifier.fillMaxWidth(),
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
                    text = "🏃",
                    fontSize = 16.sp,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Muscle Analysis",
                    color = colorScheme.onBackground,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Agonist (Prime Mover)
            val agonist = muscleDetails.find { it.detailCategory == "주동근" }
            agonist?.let {
                MuscleDetailRow(
                    label = "AGONIST (PRIME MOVER)",
                    value = it.description ?: "",
                    accentColor = colorScheme.primary,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Antagonist
            val antagonist = muscleDetails.find { it.detailCategory == "길항근" }
            antagonist?.let {
                MuscleDetailRow(
                    label = "ANTAGONIST",
                    value = it.description ?: "",
                    accentColor = colorScheme.onSurfaceVariant,
                )
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

            details.forEach { detail ->
                detail.detailCategory?.let { category ->
                    Text(
                        text = category,
                        color = colorScheme.primary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
                detail.description?.let { description ->
                    Text(
                        text = description,
                        color = colorScheme.onSurface,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                    )
                }
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
                detail.description?.let { description ->
                    Text(
                        text = description,
                        color = colorScheme.onSurface,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                    )
                }
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
    
    // 편집 가능한 상태들
    var editTitle by remember { mutableStateOf(title) }
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
                text = "편집",
                color = colorScheme.onBackground,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        // 제목 필드
        OutlinedTextField(
            value = editTitle,
            onValueChange = { editTitle = it },
            label = { Text("제목") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Primary 필드
        OutlinedTextField(
            value = primaryValue,
            onValueChange = { primaryValue = it },
            label = { Text("Primary") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Secondary 필드
        OutlinedTextField(
            value = secondaryValue,
            onValueChange = { secondaryValue = it },
            label = { Text("Secondary") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // 근위/원위 필드
        OutlinedTextField(
            value = proximalDistalValue,
            onValueChange = { proximalDistalValue = it },
            label = { Text("근위/원위") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // 주동근 필드
        OutlinedTextField(
            value = agonistValue,
            onValueChange = { agonistValue = it },
            label = { Text("주동근") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // 길항근 필드
        OutlinedTextField(
            value = antagonistValue,
            onValueChange = { antagonistValue = it },
            label = { Text("길항근") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
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
    
    // 편집 가능한 상태들
    var editTitle by remember { mutableStateOf(title) }
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
                text = "편집",
                color = colorScheme.onBackground,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        // 제목 필드
        OutlinedTextField(
            value = editTitle,
            onValueChange = { editTitle = it },
            label = { Text("제목") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Description 필드
        OutlinedTextField(
            value = descriptionValue,
            onValueChange = { descriptionValue = it },
            label = { Text("설명") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            maxLines = 5,
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
                            contractionType = editTitle,
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
