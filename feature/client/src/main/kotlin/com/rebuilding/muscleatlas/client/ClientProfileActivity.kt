package com.rebuilding.muscleatlas.client

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.rebuilding.muscleatlas.client.screen.ClientProfileScreen
import com.rebuilding.muscleatlas.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClientProfileActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isDarkTheme = intent.getBooleanExtra("theme", isSystemInDarkTheme())
            val clientId = intent.getStringExtra("clientId") ?: ""

            ClientProfileScreen(
                clientId = clientId,
                isDarkTheme = isDarkTheme,
                onClickBack = { finish() }
            )
        }
    }
}