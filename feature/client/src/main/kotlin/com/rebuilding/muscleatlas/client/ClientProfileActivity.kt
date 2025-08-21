package com.rebuilding.muscleatlas.client

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.rebuilding.muscleatlas.client.screen.ClientProfileScreen

class ClientProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClientProfileScreen(
                intent = intent,
                onClickBack = { finish() }
            )
        }
    }
}