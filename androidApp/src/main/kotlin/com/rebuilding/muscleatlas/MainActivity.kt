package com.rebuilding.muscleatlas

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.rebuilding.muscleatlas.appconfig.AppConfig
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.handleDeeplinks
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {

    private val supabaseClient: SupabaseClient by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Koin이 아직 초기화되지 않은 경우에만 시작
        if (GlobalContext.getOrNull() == null) {
            startKoin(
                muscleAtlasAppDeclaration {
                    androidContext(this@MainActivity)
                },
            )
        }

        val isLoggedIn = isLoggedIn(intent)
        supabaseClient.handleDeeplinks(intent)

        setContent {
            App(
                isLoggedIn = isLoggedIn
            )
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        supabaseClient.handleDeeplinks(intent)
    }

    private fun isLoggedIn(intent: Intent): Boolean {
        val loginCallbackUri =
            "${AppConfig.SUPABASE_LOGIN_SCHEME}://${AppConfig.SUPABASE_LOGIN_HOST}"
        return intent.action == Intent.ACTION_VIEW &&
                intent.data?.toString()?.startsWith(loginCallbackUri) == true
    }
}
