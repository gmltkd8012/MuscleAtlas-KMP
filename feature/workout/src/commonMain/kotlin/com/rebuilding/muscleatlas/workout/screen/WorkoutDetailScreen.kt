package com.rebuilding.muscleatlas.workout.screen

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.rebuilding.muscleatlas.designsystem.theme.AppColors
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

    LaunchedEffect(exerciseId) {
        viewModel.loadExerciseDetail(exerciseId)
    }

    Scaffold(
        containerColor = AppColors.exerciseDetailBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Exercise Detail",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "뒤로가기",
                            tint = Color.White,
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Bookmark */ }) {
                        Icon(
                            imageVector = Icons.Default.BookmarkBorder,
                            contentDescription = "북마크",
                            tint = Color.White,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColors.exerciseDetailBackground,
                ),
            )
        },
        bottomBar = {
            // Add to Routine Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppColors.exerciseDetailBackground)
                    .padding(16.dp),
            ) {
                Button(
                    onClick = { /* TODO: Add to routine */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(26.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.accentCyan,
                    ),
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.White,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Add to Routine",
                        color = Color.White,
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
                    CircularProgressIndicator(color = AppColors.accentCyan)
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
                        color = Color.White,
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
                            color = Color.White,
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

                    // Technical Breakdown Section
                    val technicalDetails = state.groupedDetails["기계적 움직임"]
                        ?.filterKeys { it != "Eccentric" && it != "Concentric" && it != "근육 분석" }

                    if (!technicalDetails.isNullOrEmpty()) {
                        item {
                            SectionTitle(title = "TECHNICAL BREAKDOWN")
                        }

                        technicalDetails.forEach { (contractionType, details) ->
                            item {
                                TechnicalCard(
                                    title = contractionType,
                                    details = details,
                                    iconColor = AppColors.accentCyan,
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
}

@Composable
private fun HeroImageSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        AppColors.cardBackground,
                        AppColors.exerciseDetailBackground,
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
                color = AppColors.sectionTitle,
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
        ExerciseTag(text = "Strength")
        ExerciseTag(text = "Legs")
    }
}

@Composable
private fun ExerciseTag(
    text: String,
    isPrimary: Boolean = false,
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (isPrimary) AppColors.accentCyan.copy(alpha = 0.2f)
                else AppColors.cardBackground,
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
    ) {
        Text(
            text = text,
            color = if (isPrimary) AppColors.accentCyan else AppColors.descriptionText,
            fontSize = 12.sp,
            fontWeight = if (isPrimary) FontWeight.Bold else FontWeight.Medium,
        )
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        color = AppColors.sectionTitle,
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
            iconColor = AppColors.accentOrange,
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
            iconColor = AppColors.accentCyan,
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
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.cardBackground,
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
                    color = AppColors.sectionTitle,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                )
                Text(
                    text = item.value,
                    color = Color.White,
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
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.cardBackground,
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
                    color = Color.White,
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
                    accentColor = AppColors.accentCyan,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Antagonist
            val antagonist = muscleDetails.find { it.detailCategory == "길항근" }
            antagonist?.let {
                MuscleDetailRow(
                    label = "ANTAGONIST",
                    value = it.description ?: "",
                    accentColor = AppColors.sectionTitle,
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
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 12.dp, top = 4.dp),
        )
    }
}

@Composable
private fun TechnicalCard(
    title: String,
    details: List<ExerciseDetail>,
    iconColor: Color,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.cardBackground,
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
                        .background(iconColor.copy(alpha = 0.2f), RoundedCornerShape(4.dp)),
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
                    color = iconColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            details.forEach { detail ->
                detail.detailCategory?.let { category ->
                    Text(
                        text = category,
                        color = AppColors.accentCyan,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
                detail.description?.let { description ->
                    Text(
                        text = description,
                        color = AppColors.descriptionText,
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
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.cardBackground,
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
                    title.contains("ROM", ignoreCase = true) -> AppColors.accentOrange
                    else -> AppColors.accentBlue
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
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            details.forEach { detail ->
                detail.description?.let { description ->
                    Text(
                        text = description,
                        color = AppColors.descriptionText,
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
