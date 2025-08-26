package com.rebuilding.muscleatlas.design_system.component

import android.R.attr.maxLength
import android.R.attr.text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rebuilding.muscleatlas.design_system.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseText
import com.rebuilding.muscleatlas.model.Movement
import com.rebuilding.muscleatlas.ui.extension.clickableWithoutIndication
import com.rebuilding.muscleatlas.ui.extension.selectableWithoutIndication
import kotlin.collections.forEachIndexed
import kotlin.collections.get

@Composable
fun BaseTabRow(
    modifier: Modifier = Modifier,
    tabList: List<Movement>,
    onTabSelected:(Int) -> Unit,
    currentTabIndex: Int,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .wrapContentSize()
            .background(color = AppColors.color.primary)
    ) {
        TabRow(
            selectedTabIndex =  currentTabIndex,
            indicator = @Composable { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[currentTabIndex])
                        .fillMaxWidth(),
                    height = 2.dp,
                    color = AppColors.color.onPrimary,
                )
            },
            divider = {}
        ) {
            tabList.forEachIndexed { index, title ->
                BaseTab (
                    text = tabList[index].title,
                    selected = currentTabIndex == index,
                    onTabSelected = {
                        onTabSelected(index)
                    }
                )
            }
        }

        content()
    }
}

@Composable
fun BaseTab(
    text: String,
    selected: Boolean,
    onTabSelected: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box (
        modifier = modifier
            .height(64.dp)
            .background(AppColors.color.primary)
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .selectableWithoutIndication(
                selected = selected,
                onClick = onTabSelected,
                role = Role.Tab,
            ),
        contentAlignment = Alignment.Center
    ) {
        BaseText(
            text = text,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = if (selected) AppColors.color.onPrimary else AppColors.color.onSecondary,
            textAlign = TextAlign.Center
        )
    }
}