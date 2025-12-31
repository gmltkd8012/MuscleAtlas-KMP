package com.rebuilding.muscleatlas

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rebuilding.muscleatlas.designsystem.theme.MuscleAtlasTheme
import com.rebuilding.muscleatlas.di.appModule
import com.rebuilding.muscleatlas.login.di.loginModule
import com.rebuilding.muscleatlas.member.di.memberModule
import com.rebuilding.muscleatlas.navigation.MuscleAtlasNavHost
import com.rebuilding.muscleatlas.setting.di.settingModule
import com.rebuilding.muscleatlas.splash.di.splashModule
import com.rebuilding.muscleatlas.splash.navigation.SplashRoute
import com.rebuilding.muscleatlas.supabase.di.supabaseModule
import com.rebuilding.muscleatlas.workout.di.workoutModule
import org.koin.core.KoinApplication
import org.koin.dsl.KoinAppDeclaration

@Composable
fun App() {
    MuscleAtlasTheme {
        MuscleAtlasNavHost(
            startDestination = SplashRoute,
            modifier = Modifier.fillMaxSize()
        )
    }
}

fun muscleAtlasAppDeclaration(
    platformDeclaration: KoinApplication.() -> Unit = {},
): KoinAppDeclaration = {

    /* Feature 모듈 의존성 */
    modules(
        appModule,
        splashModule,
        loginModule,
        memberModule,
        settingModule,
        workoutModule,
    )

    modules(supabaseModule)
    platformDeclaration()
}
