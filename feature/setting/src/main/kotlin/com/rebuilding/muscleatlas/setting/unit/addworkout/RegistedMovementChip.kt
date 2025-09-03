package com.rebuilding.muscleatlas.setting.unit.addworkout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rebuilding.muscleatlas.design_system.theme.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseImageButton
import com.rebuilding.muscleatlas.design_system.base.BaseLine
import com.rebuilding.muscleatlas.design_system.base.BaseText
import com.rebuilding.muscleatlas.design_system.component.BaseTabRow
import com.rebuilding.muscleatlas.design_system.component.SwipeItemChip
import com.rebuilding.muscleatlas.model.Movement
import com.rebuilding.muscleatlas.model.MovementData
import com.rebuilding.muscleatlas.setting.unit.workoutmanage.WorkoutManageChip
import com.rebuilding.muscleatlas.util.MovementUtils
import kotlinx.coroutines.launch

@Composable
fun RegistedMovementChip(
    joinMovementList: List<MovementData> = emptyList<MovementData>(),
    stabilizationMechanismList: List<MovementData> = emptyList<MovementData>(),
    muscularRelationList: List<MovementData> = emptyList<MovementData>(),
    onClickEdit: (MovementData) -> Unit = {},
    onClickAdd: (Int) -> Unit = {},
    onClickDelete: () -> Unit = {},
) {
    val pagerState = rememberPagerState(
        pageCount = { Movement.allMovements.size },
        initialPageOffsetFraction = 0f,
        initialPage = 0,
    )

    val currentTabIndex = pagerState.currentPage
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        BaseText(
            text = "세부 동작",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Start
        )

        Spacer(Modifier.height(16.dp))

        BaseTabRow(
            tabList = Movement.allMovements,
            currentTabIndex = currentTabIndex,
            onTabSelected = { index ->
                scope.launch { pagerState.scrollToPage(index) }
            }
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize(),
                userScrollEnabled = true,
            ) {
                Column {
                    Spacer(Modifier.height(16.dp))

                    when(currentTabIndex) {
                        MovementUtils.TYPE_JOIN_MOVEMENT -> {
                            if (joinMovementList.isEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .height(200.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    BaseText(
                                        text = "${Movement.JoinMovement.title}을 등록해 주세요.",
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight(500)
                                        ),
                                        color = MaterialTheme.colorScheme.onSecondary
                                    )
                                }
                            } else {
                                joinMovementList.forEachIndexed { index, data ->
                                    SwipeItemChip(
                                        onDelete = onClickDelete
                                    ) {
                                        WorkoutManageChip(
                                            name = data.title,
                                            //modifier = Modifier.padding(vertical = 16.dp),
                                            onClick = { onClickEdit(data) }
                                        )
                                    }

                                    if (index != joinMovementList.lastIndex) {
                                        BaseLine(
                                            lineColor = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                }
                            }
                        }

                        MovementUtils.TYPE_STABILIZATION_MECHANISM -> {
                            if (stabilizationMechanismList.isEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .height(200.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    BaseText(
                                        text = "${Movement.StabilizationMechanism.title}을 등록해 주세요.",
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight(500)
                                        ),
                                        color = MaterialTheme.colorScheme.onSecondary
                                    )
                                }
                            } else {
                                stabilizationMechanismList.forEachIndexed { index, data ->
                                    SwipeItemChip(
                                        onDelete = onClickDelete
                                    ) {
                                        WorkoutManageChip(
                                            name = data.title,
                                            //modifier = Modifier.padding(vertical = 16.dp),
                                            onClick = { onClickEdit(data) }
                                        )
                                    }

                                    if (index != stabilizationMechanismList.lastIndex) {
                                        BaseLine(
                                            lineColor = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                }
                            }
                        }

                        MovementUtils.TYPE_MUSCULAR_RELATION -> {
                            if (muscularRelationList.isEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .height(200.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    BaseText(
                                        text = "${Movement.NeuromuscularRelation.title}을 등록해 주세요.",
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight(500)
                                        ),
                                        color = MaterialTheme.colorScheme.onSecondary
                                    )
                                }
                            } else {
                                muscularRelationList.forEachIndexed { index, data ->
                                    SwipeItemChip(
                                        onDelete = onClickDelete
                                    ) {
                                        WorkoutManageChip(
                                            name = data.title,
                                            //modifier = Modifier.padding(vertical = 16.dp),
                                            onClick = { onClickEdit(data) }
                                        )
                                    }

                                    if (index != muscularRelationList.lastIndex) {
                                        BaseLine(
                                            lineColor = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        BaseImageButton(
            modifier = Modifier.align(Alignment.End),
            text = "${Movement.allMovements[currentTabIndex].title} 종목 추가",
            icon = Icons.Default.Add,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.1f),
            onClick = {
                onClickAdd(currentTabIndex)
            }
        )
    }
}

@Preview
@Composable
private fun RegistedMovementChipPreview() {
    RegistedMovementChip()
}

@Preview
@Composable
private fun RegistedMovementChipEmptyPreview() {
    RegistedMovementChip()
}
