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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rebuilding.muscleatlas.design_system.theme.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseImageButton
import com.rebuilding.muscleatlas.design_system.base.BaseLine
import com.rebuilding.muscleatlas.design_system.base.BaseText
import com.rebuilding.muscleatlas.design_system.component.BaseTabRow
import com.rebuilding.muscleatlas.design_system.component.SwipeItemChip
import com.rebuilding.muscleatlas.model.Movement
import com.rebuilding.muscleatlas.setting.unit.workoutmanage.WorkoutManageChip
import kotlinx.coroutines.launch

@Composable
fun RegistedMovementChip(
    movementList: List<String> = emptyList<String>(),
    onClickEdit: () -> Unit = {},
    onClickAdd: () -> Unit = {},
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

        Spacer(Modifier.height(8.dp))

        if (movementList.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                BaseText(
                    text = "세부 동작을 등록해 주세요.",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight(500)
                    ),
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        } else {
            Spacer(Modifier.height(8.dp))

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

                        movementList.forEachIndexed { index, name ->
                            SwipeItemChip(
                                onDelete = onClickDelete
                            ) {
                                WorkoutManageChip(
                                    name = "${Movement.allMovements[currentTabIndex].title} $index",
                                    //modifier = Modifier.padding(vertical = 16.dp),
                                    onClick = onClickEdit
                                )
                            }

                            if (index != movementList.lastIndex) {
                                BaseLine(
                                    lineColor = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                    }



//                    when (currentTabIndex) {
//                        0 -> {
//
//                        }
//                        1 -> {
//
//                        }
//                        2 -> {
//
//                        }
//
//
//                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        BaseImageButton(
            modifier = Modifier.align(Alignment.End),
            text = "${Movement.allMovements[currentTabIndex].title} 종목 추가",
            icon = Icons.Default.Add,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.1f),
            onClick = onClickAdd
        )
    }
}
