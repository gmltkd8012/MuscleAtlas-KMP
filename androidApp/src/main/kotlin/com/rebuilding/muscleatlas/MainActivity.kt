package com.rebuilding.muscleatlas

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.rebuilding.muscleatlas.app.App
import com.rebuilding.muscleatlas.app.muscleAtlasAppDeclaration
import com.rebuilding.muscleatlas.appconfig.AppConfig
import dev.yjyoon.kinappbrowser.KInAppBrowser
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.handleDeeplinks
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {

    private val supabaseClient: SupabaseClient by lazy { 
        GlobalContext.get().get<SupabaseClient>() 
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // 시스템 스플래시 화면 설정 (super.onCreate 전에 호출해야 함)
        installSplashScreen()
        
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        KInAppBrowser.init(this)

        // Koin이 아직 초기화되지 않은 경우에만 시작
        if (GlobalContext.getOrNull() == null) {
            startKoin(
                muscleAtlasAppDeclaration {
                    androidContext(this@MainActivity)
                },
            )
        }

        // Koin 초기화 후 딥링크 처리
        supabaseClient.handleDeeplinks(intent)

        setContent {
            App()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        supabaseClient.handleDeeplinks(intent)
    }
}
