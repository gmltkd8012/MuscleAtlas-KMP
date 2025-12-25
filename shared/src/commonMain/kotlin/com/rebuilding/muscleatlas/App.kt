package com.rebuilding.muscleatlas

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.rebuilding.muscleatlas.supabase.di.supabaseModule
import org.koin.core.KoinApplication
import org.koin.dsl.KoinAppDeclaration

@Composable
fun App() {
    val platform = getPlatform()
    val uriHandler = LocalUriHandler.current

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    modifier = Modifier.width(100.dp).height(50.dp),
                    onClick = {
                        platform.storeLink?.let { url ->
                            uriHandler.openUri(url)
                        }
                    }
                ) {
                    Text("${platform.name}")
                }
            }
        }
    }
}

fun muscleAtlasAppDeclaration(
    platformDeclaration: KoinApplication.() -> Unit = {},
): KoinAppDeclaration = {
    modules(supabaseModule)
}
