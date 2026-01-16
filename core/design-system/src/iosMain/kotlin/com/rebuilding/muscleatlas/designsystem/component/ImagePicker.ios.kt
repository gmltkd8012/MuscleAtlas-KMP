package com.rebuilding.muscleatlas.designsystem.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import platform.Foundation.NSData
import platform.PhotosUI.PHPickerConfiguration
import platform.PhotosUI.PHPickerConfigurationSelectionOrdered
import platform.PhotosUI.PHPickerFilter
import platform.PhotosUI.PHPickerResult
import platform.PhotosUI.PHPickerViewController
import platform.PhotosUI.PHPickerViewControllerDelegateProtocol
import platform.UIKit.UIApplication
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.darwin.NSObject
import platform.posix.memcpy

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun rememberImagePickerLauncher(
    onResult: (ImagePickerResult) -> Unit
): ImagePickerLauncher {
    return remember {
        IosImagePickerLauncher(onResult)
    }
}

@OptIn(ExperimentalForeignApi::class)
private class IosImagePickerLauncher(
    private val onResult: (ImagePickerResult) -> Unit
) : ImagePickerLauncher {

    override fun launch() {
        val configuration = PHPickerConfiguration().apply {
            filter = PHPickerFilter.imagesFilter
            selectionLimit = 1
            selection = PHPickerConfigurationSelectionOrdered
        }

        val picker = PHPickerViewController(configuration = configuration)
        picker.delegate = object : NSObject(), PHPickerViewControllerDelegateProtocol {
            override fun picker(picker: PHPickerViewController, didFinishPicking: List<*>) {
                picker.dismissViewControllerAnimated(true, completion = null)

                val results = didFinishPicking.filterIsInstance<PHPickerResult>()
                if (results.isEmpty()) {
                    onResult(ImagePickerResult.Cancelled)
                    return
                }

                val result = results.first()
                val itemProvider = result.itemProvider

                // "public.image" UTType을 사용하여 이미지 데이터 로드
                if (itemProvider.hasItemConformingToTypeIdentifier("public.image")) {
                    itemProvider.loadDataRepresentationForTypeIdentifier(
                        typeIdentifier = "public.image",
                        completionHandler = { data, error ->
                            if (error != null) {
                                onResult(ImagePickerResult.Error(error.localizedDescription))
                                return@loadDataRepresentationForTypeIdentifier
                            }

                            if (data == null) {
                                onResult(ImagePickerResult.Error("Failed to load image data"))
                                return@loadDataRepresentationForTypeIdentifier
                            }

                            val bytes = data.toByteArray()
                            onResult(ImagePickerResult.Success(bytes))
                        }
                    )
                } else {
                    onResult(ImagePickerResult.Error("Cannot load image"))
                }
            }
        }

        val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
        rootViewController?.presentViewController(picker, animated = true, completion = null)
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun NSData.toByteArray(): ByteArray {
    val size = length.toInt()
    val bytes = ByteArray(size)
    if (size > 0) {
        memcpy(bytes.refTo(0), this.bytes, this.length)
    }
    return bytes
}
