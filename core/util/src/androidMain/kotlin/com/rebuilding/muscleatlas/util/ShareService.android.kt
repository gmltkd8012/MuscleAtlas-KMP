package com.rebuilding.muscleatlas.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.template.model.Button
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import com.rebuilding.muscleatlas.ui.util.Logger

actual class ShareService(
    private val context: Context,
) {
    actual fun shareToKakao(
        title: String,
        description: String,
        imageUrl: String?,
        linkUrl: String,
    ) {
        val defaultFeed = FeedTemplate(
            content = Content(
                title = title,
                description = description,
                imageUrl = imageUrl ?: "https://via.placeholder.com/300x200?text=MuscleAtlas",
                link = Link(
                    webUrl = linkUrl,
                    mobileWebUrl = linkUrl,
                ),
            ),
            buttons = listOf(
                Button(
                    title = "자세히 보기",
                    link = Link(
                        webUrl = linkUrl,
                        mobileWebUrl = linkUrl,
                    ),
                ),
            ),
        )

        // 카카오톡 설치 여부 확인
        if (ShareClient.instance.isKakaoTalkSharingAvailable(context)) {
            ShareClient.instance.shareDefault(context, defaultFeed) { sharingResult, error ->
                if (error != null) {
                    Logger.e("KakaoShare","카카오톡 공유 실패: ${error.message}")
                    // 실패 시 시스템 공유로 대체
                    shareText(title, "$description\n\n$linkUrl")
                } else if (sharingResult != null) {
                    Logger.d("KakaoShare","카카오톡 공유 성공")

                    try {
                        context.startActivity(sharingResult.intent)
                    } catch (e: ActivityNotFoundException) {
                        Logger.e("KakaoShare","Activity not found: ${e.message}")
                        shareText(title, "$description\n\n$linkUrl")
                    }
                }
            }
        } else {
            // 카카오톡 미설치 시 웹 공유
            Logger.d("KakaoShare","카카오톡 미설치, 시스템 공유 사용")
            shareText(title, "$description\n\n$linkUrl")
        }
    }

    actual fun shareText(
        title: String,
        text: String,
    ) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, title)
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(shareIntent)
    }
}

@Composable
actual fun rememberShareService(): ShareService {
    val context = LocalContext.current
    return remember { ShareService(context) }
}
