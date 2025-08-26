package com.rebuilding.muscleatlas.client.screen

import android.R.attr.fontWeight
import android.R.attr.onClick
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rebuilding.muscleatlas.design_system.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseButton
import com.rebuilding.muscleatlas.design_system.base.BaseText
import com.rebuilding.muscleatlas.design_system.base.BaseTextBox
import com.rebuilding.muscleatlas.design_system.component.BaseTopBar
import com.rebuilding.muscleatlas.design_system.component.PrimaryButton
import com.rebuilding.muscleatlas.design_system.component.ProfileChip
import com.rebuilding.muscleatlas.ui.extension.clickableWithoutIndication

@Composable
fun MovementDetailScreen(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    imgUrl: ImageVector?,
    onClickBack: () -> Unit = {},
) {
    Scaffold(
        modifier = Modifier.background(AppColors.primary),
        topBar = {
            BaseTopBar(
                title = {
                    BaseText(
                        text = title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = AppColors.onPrimary,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                },
                backIcon = {
                    BaseButton(
                        modifier = Modifier,
                        icon = Icons.Default.Close,
                        iconSize = 30.dp,
                    ) {
                        onClickBack()
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppColors.primary)
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .background(color = AppColors.onPrimary.copy(alpha = 0.1f))
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                if (imgUrl != null) {
                    Image(
                        imageVector = imgUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )
                } else {
                    BaseText(
                        text = "No Image",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500)
                        ),
                        color = AppColors.onSecondary,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            BaseText(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                color = AppColors.onSecondary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}