package com.rebuilding.muscleatlas.member.component

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

/**
 * Member 모듈용 리스트 아이템
 * - 프로필 영역: 동그란 형태 (CircleShape)
 * - 전체 사이즈: 작게 (40dp 프로필, 12dp 패딩)
 */
@Composable
fun MemberListItem(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    profileContent: @Composable (BoxScope.() -> Unit)? = null,
) {
    val colorScheme = MaterialTheme.colorScheme

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(colorScheme.surface),
                contentAlignment = Alignment.Center,
            ) {
                if (profileContent != null) {
                    profileContent()
                } else {
                    Text(
                        text = title.firstOrNull()?.uppercase() ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colorScheme.primary,
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
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
