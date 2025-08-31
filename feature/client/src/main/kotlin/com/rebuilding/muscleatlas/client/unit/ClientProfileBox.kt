package com.rebuilding.muscleatlas.client.unit

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rebuilding.muscleatlas.design_system.theme.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseText
import com.rebuilding.muscleatlas.design_system.component.ProfileChip

@Composable
fun ClientProfileBox(
    clientName: String,
    memo: String,
    workoutName: String = "",
    isColumn: Boolean = true,
) {
    AnimatedContent(
        targetState = isColumn,
        transitionSpec = {
            fadeIn(tween(300)) togetherWith fadeOut(tween(300))
        }
    ) { showColumn ->
        if (showColumn) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileChip(
                    name = clientName,
                    size = 108.dp,
                )

                Spacer(Modifier.height(12.dp))

                BaseText(
                    text = clientName,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onPrimary
                )

                Spacer(Modifier.height(6.dp))

                BaseText(
                    text = memo,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 16.sp,
                    ),
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        } else {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(horizontal = 12.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ProfileChip(
                        name = clientName,
                        size = 72.dp,
                    )

                    Spacer(Modifier.width(12.dp))

                    Column {
                        BaseText(
                            text = clientName,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        Spacer(Modifier.height(6.dp))

                        BaseText(
                            text = memo,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontSize = 14.sp,
                            ),
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(end = 12.dp)
                ) {
                    BaseText(
                        text = workoutName,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight(600)
                        ),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ClientProfileBoxPreview() {
    ClientProfileBox(
        clientName = "테스터",
        memo = "테스트",
        workoutName = "스쿼트",
        isColumn = false
    )
}