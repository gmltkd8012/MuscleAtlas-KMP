package com.rebuilding.muscleatlas.design_system.component

import android.R.attr.name
import android.R.attr.onClick
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rebuilding.muscleatlas.design_system.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseText
import com.rebuilding.muscleatlas.ui.extension.clickableWithoutIndication
import com.rebuilding.muscleatlas.util.getFirstWord

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .background(
                color = AppColors.primaryButton,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .clickableWithoutIndication {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        BaseText(
            text = text,
            style =  MaterialTheme.typography.titleMedium.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            ),
            color = AppColors.primaryButtonText
        )
    }
}

@Preview(showBackground = true)
@Composable
fun  PrimaryButtonPreview() {
    PrimaryButton(
        text = "버튼명"
    )
}