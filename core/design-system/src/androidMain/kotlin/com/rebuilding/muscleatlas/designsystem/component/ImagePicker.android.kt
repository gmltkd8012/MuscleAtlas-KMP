package com.rebuilding.muscleatlas.designsystem.component

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberImagePickerLauncher(
    onResult: (ImagePickerResult) -> Unit
): ImagePickerLauncher {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            try {
                val bytes = context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    inputStream.readBytes()
                }
                if (bytes != null) {
                    onResult(ImagePickerResult.Success(bytes))
                } else {
                    onResult(ImagePickerResult.Error("Failed to read image"))
                }
            } catch (e: Exception) {
                onResult(ImagePickerResult.Error(e.message ?: "Unknown error"))
            }
        } else {
            onResult(ImagePickerResult.Cancelled)
        }
    }

    return remember {
        object : ImagePickerLauncher {
            override fun launch() {
                launcher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }
        }
    }
}
