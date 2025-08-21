package com.rebuilding.muscleatlas.client

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.rebuilding.muscleatlas.client.screen.ClientProfileScreen
import com.rebuilding.muscleatlas.ui.base.BaseActivity

class ClientProfileActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @Composable
    override fun ProvideComposableScreen() {
        super.ProvideComposableScreen()
        ClientProfileScreen(
            intent = intent,
            onClickBack = { finish() }
        )
    }
}