package com.rebuilding.muscleatlas.workout.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.rebuilding.muscleatlas.designsystem.component.PhotoBox
import com.rebuilding.muscleatlas.designsystem.component.PhotoBoxState
import com.rebuilding.muscleatlas.designsystem.component.rememberPhotoBoxState

/**
 * Workout 모듈용 리스트 아이템
 * - 프로필 영역: 살짝 네모난 형태 (RoundedCornerShape 8dp)
 * - 전체 사이즈: 크게 (64dp 프로필, 16dp 패딩)
 */
@Composable
fun WorkoutListItem(
    title: String,
    imgUrl: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colorScheme = MaterialTheme.colorScheme
    val photoBoxState = rememberPhotoBoxState(initialUrl = imgUrl)

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(colorScheme.surface),
                contentAlignment = Alignment.Center,
            ) {
                if (imgUrl != null) {
                    PhotoBox(
                        state = photoBoxState,
                        modifier = Modifier.size(64.dp),
                        shape = RoundedCornerShape(8.dp),
                        enabled = false,
                    )
                } else {
                    Text(
                        text = title.firstOrNull()?.uppercase() ?: "",
                        style = MaterialTheme.typography.titleMedium,
                        color = colorScheme.primary,
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = colorScheme.onBackground,
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = colorScheme.outline,
            thickness = 1.dp,
        )
    }
}
