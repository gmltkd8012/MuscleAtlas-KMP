package com.rebuilding.muscleatlas.designsystem.component

import androidx.compose.runtime.Composable

/**
 * 이미지 선택 결과를 나타내는 sealed class
 */
sealed class ImagePickerResult {
    data class Success(val imageBytes: ByteArray) : ImagePickerResult() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false
            other as Success
            return imageBytes.contentEquals(other.imageBytes)
        }

        override fun hashCode(): Int = imageBytes.contentHashCode()
    }

    data object Cancelled : ImagePickerResult()
    data class Error(val message: String) : ImagePickerResult()
}

/**
 * 플랫폼별 이미지 피커 런처 인터페이스
 */
interface ImagePickerLauncher {
    fun launch()
}

/**
 * 플랫폼별 이미지 피커를 생성하는 Composable 함수
 * @param onResult 이미지 선택 결과 콜백
 * @return ImagePickerLauncher 인스턴스
 */
@Composable
expect fun rememberImagePickerLauncher(
    onResult: (ImagePickerResult) -> Unit
): ImagePickerLauncher
