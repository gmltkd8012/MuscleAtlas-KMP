package com.rebuilding.muscleatlas

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.rebuilding.muscleatlas.appconfig.AppConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MuscleAtlasApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        
        // Kakao SDK 초기화
        KakaoSdk.init(this, AppConfig.KAKAO_NATIVE_APP_KEY)
    }
}
