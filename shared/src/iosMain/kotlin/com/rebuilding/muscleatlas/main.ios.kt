package com.rebuilding.muscleatlas

import androidx.compose.ui.uikit.OnFocusBehavior
import androidx.compose.ui.window.ComposeUIViewController
import com.rebuilding.muscleatlas.app.App
import com.rebuilding.muscleatlas.app.muscleAtlasAppDeclaration
import org.koin.compose.KoinApplication

fun MainViewController() = ComposeUIViewController(
    configure = {
        onFocusBehavior = OnFocusBehavior.DoNothing
    }
) {
    KoinApplication(
        application = muscleAtlasAppDeclaration()
    ) {
        App()
    }
}
