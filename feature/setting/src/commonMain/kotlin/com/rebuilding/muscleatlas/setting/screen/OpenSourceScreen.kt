package com.rebuilding.muscleatlas.setting.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenSourceScreen(
    onNavigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("오픈소스 라이선스") },
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {
            item {
                Text(
                    text = "이 앱은 다음의 오픈소스 라이브러리를 사용합니다.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 16.dp),
                )
            }

            items(openSourceLibraries) { library ->
                OpenSourceItem(library = library)
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun OpenSourceItem(
    library: OpenSourceLibrary,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = library.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Version ${library.version}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = library.license,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium,
            )

            if (library.description.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = library.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

private data class OpenSourceLibrary(
    val name: String,
    val version: String,
    val license: String,
    val description: String = "",
)

private val openSourceLibraries = listOf(
    // Kotlin & JetBrains
    OpenSourceLibrary(
        name = "Kotlin",
        version = "2.2.0",
        license = "Apache License 2.0",
        description = "The Kotlin Programming Language",
    ),
    OpenSourceLibrary(
        name = "Compose Multiplatform",
        version = "1.8.0",
        license = "Apache License 2.0",
        description = "Declarative UI framework for Kotlin Multiplatform",
    ),
    OpenSourceLibrary(
        name = "Kotlinx Coroutines",
        version = "1.10.1",
        license = "Apache License 2.0",
        description = "Library support for Kotlin coroutines",
    ),
    OpenSourceLibrary(
        name = "Kotlinx Serialization",
        version = "1.8.0",
        license = "Apache License 2.0",
        description = "Kotlin serialization library",
    ),
    OpenSourceLibrary(
        name = "Kotlinx Datetime",
        version = "0.6.1",
        license = "Apache License 2.0",
        description = "Multiplatform date/time library",
    ),

    // AndroidX
    OpenSourceLibrary(
        name = "AndroidX Core KTX",
        version = "1.15.0",
        license = "Apache License 2.0",
        description = "Kotlin extensions for Android core libraries",
    ),
    OpenSourceLibrary(
        name = "AndroidX Lifecycle",
        version = "2.8.7",
        license = "Apache License 2.0",
        description = "Lifecycle-aware components",
    ),
    OpenSourceLibrary(
        name = "AndroidX Activity Compose",
        version = "1.10.1",
        license = "Apache License 2.0",
        description = "Compose integration with Activity",
    ),
    OpenSourceLibrary(
        name = "AndroidX Navigation Compose",
        version = "2.9.1",
        license = "Apache License 2.0",
        description = "Navigation component for Compose",
    ),
    OpenSourceLibrary(
        name = "AndroidX DataStore",
        version = "1.1.1",
        license = "Apache License 2.0",
        description = "Data storage solution for key-value pairs",
    ),

    // DI & Network
    OpenSourceLibrary(
        name = "Koin",
        version = "4.0.4",
        license = "Apache License 2.0",
        description = "Dependency injection framework for Kotlin",
    ),
    OpenSourceLibrary(
        name = "Ktor",
        version = "3.1.0",
        license = "Apache License 2.0",
        description = "Asynchronous HTTP client for Kotlin Multiplatform",
    ),

    // Backend
    OpenSourceLibrary(
        name = "Supabase Kotlin",
        version = "3.2.4",
        license = "MIT License",
        description = "Kotlin Multiplatform client for Supabase",
    ),

    // Utilities
    OpenSourceLibrary(
        name = "BuildKonfig",
        version = "0.17.1",
        license = "Apache License 2.0",
        description = "BuildConfig for Kotlin Multiplatform",
    ),
    OpenSourceLibrary(
        name = "KInAppBrowser",
        version = "1.0.0",
        license = "Apache License 2.0",
        description = "In-app browser for Kotlin Multiplatform by yjyoon-dev",
    ),

    // Kakao
    OpenSourceLibrary(
        name = "Kakao SDK",
        version = "2.20.6",
        license = "Apache License 2.0",
        description = "Kakao platform SDK for Android",
    ),
)
