package com.rebuilding.muscleatlas.supabase.di

import com.rebuilding.muscleatlas.appconfig.AppConfig
import com.rebuilding.muscleatlas.supabase.InAppBrowserLauncher
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.UrlLauncher
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.appleNativeLogin
import io.github.jan.supabase.compose.auth.googleNativeLogin
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.functions.Functions
import io.github.jan.supabase.logging.LogLevel
import io.github.jan.supabase.postgrest.Postgrest
import org.koin.dsl.module

@OptIn(SupabaseExperimental::class)
val supabaseModule =
    module {
        single {
            createSupabaseClient(
                supabaseUrl = AppConfig.SUPABASE_URL,
                supabaseKey = AppConfig.SUPABASE_KEY,
            ) {
                install(Auth) {
                    host = AppConfig.SUPABASE_LOGIN_HOST
                    scheme = AppConfig.SUPABASE_LOGIN_SCHEME
                    urlLauncher = UrlLauncher { _, url ->
                        InAppBrowserLauncher.open(url)
                    }
                }
                install(ComposeAuth) {
                    googleNativeLogin(serverClientId = AppConfig.GOOGLE_SERVER_CLIENT_ID)
                    appleNativeLogin()
                }
                install(Postgrest)
                install(Functions)
                defaultLogLevel = LogLevel.DEBUG
            }
        }
    }
