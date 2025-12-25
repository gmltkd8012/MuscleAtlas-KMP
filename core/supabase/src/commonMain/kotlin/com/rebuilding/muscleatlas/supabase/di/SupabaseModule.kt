package com.rebuilding.muscleatlas.supabase.di

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
                supabaseUrl = "",
                supabaseKey = ""
            ) {
                install(Auth) {
                    host = ""
                    scheme = ""
                    urlLauncher =
                        UrlLauncher { supabase, url ->  }
                }
                install(ComposeAuth) {
                    googleNativeLogin(serverClientId = "")
                    appleNativeLogin()
                }
                install(Postgrest)
                install(Functions)
                defaultLogLevel = LogLevel.DEBUG
            }
        }
    }
