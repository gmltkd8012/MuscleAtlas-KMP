package com.rebuilding.muscleatlas.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rebuilding.muscleatlas.design_system.theme.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseText
import com.rebuilding.muscleatlas.model.Movement
import com.rebuilding.muscleatlas.ui.extension.selectableWithoutIndication
import kotlin.collections.forEachIndexed

@Composable
fun BaseTabRow(
    modifier: Modifier = Modifier,
    tabList: List<String>,
    onTabSelected:(Int) -> Unit,
    currentTabIndex: Int,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .wrapContentSize()
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        TabRow(
            selectedTabIndex =  currentTabIndex,
            indicator = @Composable { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[currentTabIndex])
                        .fillMaxWidth(),
                    height = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            },
            divider = {}
        ) {
            tabList.forEachIndexed { index, title ->
                BaseTab (
                    text = tabList[index],
                    selected = currentTabIndex == index,
                    onTabSelected = {
                        onTabSelected(index)
                    }
                )
            }
        }

        Spacer(Modifier.height(16.dp))
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
            .background(MaterialTheme.colorScheme.primary)
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
            color = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary,
            textAlign = TextAlign.Center
        )
    }
}