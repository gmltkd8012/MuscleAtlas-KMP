package com.rebuilding.muscleatlas

import androidx.compose.ui.uikit.OnFocusBehavior
import androidx.compose.ui.window.ComposeUIViewController
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
