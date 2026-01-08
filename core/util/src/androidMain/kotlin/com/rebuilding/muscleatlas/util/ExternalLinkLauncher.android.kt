package com.rebuilding.muscleatlas.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.rebuilding.muscleatlas.ui.util.Logger

actual class ExternalLinkLauncher(
    private val context: Context,
) {
    actual fun openInstagram(username: String) {
        val instagramAppUri = Uri.parse("instagram://user?username=$username")
        val instagramWebUri = Uri.parse("https://www.instagram.com/$username")

        try {
            // 인스타그램 앱이 설치되어 있는지 확인
            if (isInstagramInstalled()) {
                val intent = Intent(Intent.ACTION_VIEW, instagramAppUri).apply {
                    setPackage("com.instagram.android")
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
                Logger.d("ExternalLinkLauncher", "Instagram app opened for user: $username")
            } else {
                // 앱 미설치 시 브라우저로 열기
                openUrl(instagramWebUri.toString())
                Logger.d("ExternalLinkLauncher", "Instagram web opened for user: $username")
            }
        } catch (e: Exception) {
            // 실패 시 브라우저로 열기
            Logger.e("ExternalLinkLauncher", "Failed to open Instagram app: ${e.message}")
            openUrl(instagramWebUri.toString())
        }
    }

    actual fun openUrl(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Logger.e("ExternalLinkLauncher", "Failed to open URL: ${e.message}")
        }
    }

    private fun isInstagramInstalled(): Boolean {
        return try {
            context.packageManager.getPackageInfo("com.instagram.android", 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}

@Composable
actual fun rememberExternalLinkLauncher(): ExternalLinkLauncher {
    val context = LocalContext.current
    return remember { ExternalLinkLauncher(context) }
}
