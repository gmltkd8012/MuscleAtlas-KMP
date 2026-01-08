package com.rebuilding.muscleatlas.setting.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppInfoScreen(
    onNavigateBack: () -> Unit,
    onNavigateToOpenSource: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("앱 정보") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "뒤로가기",
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            // 스크롤 가능한 컨텐츠
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
            ) {
                // 앱 버전 정보
                AppInfoCard(title = "버전 정보") {
                    InfoRow(label = "앱 버전", value = "1.0.0")
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                    InfoRow(label = "빌드 번호", value = "1")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 약관 및 정책
                AppInfoCard(title = "약관 및 정책") {
                    ClickableInfoRow(
                        label = "이용약관",
                        onClick = { /* TODO: 이용약관 페이지로 이동 */ },
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                    ClickableInfoRow(
                        label = "개인정보처리방침",
                        onClick = { /* TODO: 개인정보처리방침 페이지로 이동 */ },
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                    ClickableInfoRow(
                        label = "오픈소스 라이선스",
                        onClick = onNavigateToOpenSource,
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 고객 지원
                AppInfoCard(title = "고객 지원") {
                    ClickableInfoRow(
                        label = "문의하기",
                        onClick = { /* TODO: 이메일 또는 문의 페이지로 이동 */ },
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                    ClickableInfoRow(
                        label = "자주 묻는 질문 (FAQ)",
                        onClick = { /* TODO: FAQ 페이지로 이동 */ },
                    )
                }

//                // 사업자 정보
//                AppInfoCard(title = "사업자 정보") {
//                    InfoRow(label = "상호", value = "회사명")
//                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
//                    InfoRow(label = "대표자", value = "대표자명")
//                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
//                    InfoRow(label = "사업자등록번호", value = "000-00-00000")
//                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
//                    InfoRow(label = "통신판매업신고", value = "제0000-서울강남-0000호")
//                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
//                    InfoRow(label = "사업장 주소", value = "서울특별시 강남구 ...")
//                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
//                    InfoRow(label = "대표 전화", value = "02-0000-0000")
//                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
//                    InfoRow(label = "이메일", value = "contact@example.com")
//                }
            }

            // 저작권 정보 (하단 고정)
            Text(
                text = "© 2026 LeeCoder. All rights reserved.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 60.dp),
            )
        }
    }
}

@Composable
private fun AppInfoCard(
    title: String,
    content: @Composable () -> Unit,
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                content()
            }
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
private fun ClickableInfoRow(
    label: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}