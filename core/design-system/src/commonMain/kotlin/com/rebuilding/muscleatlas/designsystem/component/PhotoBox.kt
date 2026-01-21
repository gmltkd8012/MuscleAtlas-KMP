package com.rebuilding.muscleatlas.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter

/**
 * PhotoBox의 상태를 관리하는 클래스
 */
@Stable
class PhotoBoxState {
    private var _imageBytes: ByteArray? by mutableStateOf(null)
    val imageBytes: ByteArray? get() = _imageBytes

    private var _imageUrl: String? by mutableStateOf(null)
    val imageUrl: String? get() = _imageUrl

    private var _isLoading: Boolean by mutableStateOf(false)
    val isLoading: Boolean get() = _isLoading

    /**
     * 바이트 배열로 이미지 설정
     */
    fun setImage(bytes: ByteArray?) {
        _imageBytes = bytes
        if (bytes != null) {
            _imageUrl = null
        }
    }

    /**
     * URL로 이미지 설정
     */
    fun setImageUrl(url: String?) {
        _imageUrl = url
        if (url != null) {
            _imageBytes = null
        }
    }

    /**
     * 로딩 상태 설정
     */
    fun setLoading(loading: Boolean) {
        if (_isLoading != loading) {
            _isLoading = loading
        }
    }

    /**
     * 이미지 초기화
     */
    fun clear() {
        _imageBytes = null
        _imageUrl = null
        _isLoading = false
    }

    /**
     * 이미지가 있는지 확인
     */
    val hasImage: Boolean
        get() = imageBytes != null || imageUrl != null
}

/**
 * PhotoBox 상태를 기억하는 remember 함수
 * @param initialUrl 초기 이미지 URL (선택사항)
 */
@Composable
fun rememberPhotoBoxState(
    initialUrl: String? = null
): PhotoBoxState {
    return remember {
        PhotoBoxState().apply {
            if (initialUrl != null) {
                setImageUrl(initialUrl)
            }
        }
    }
}

/**
 * 이미지 선택이 가능한 PhotoBox 컴포저블
 *
 * @param state PhotoBox 상태
 * @param modifier Modifier
 * @param size 박스 크기
 * @param shape 박스 모양 (CircleShape, RoundedCornerShape 등)
 * @param placeholderColor 플레이스홀더 배경색
 * @param borderColor 테두리 색상 (null이면 테두리 없음)
 * @param borderWidth 테두리 두께
 * @param contentScale 이미지 스케일 방식
 * @param showAddIcon 추가 아이콘 표시 여부
 * @param enabled 클릭 활성화 여부
 * @param onImageSelected 이미지 선택 완료 콜백 (바이트 배열 반환)
 */
@Composable
fun PhotoBox(
    state: PhotoBoxState,
    modifier: Modifier = Modifier,
    size: Dp = 120.dp,
    shape: Shape = CircleShape,
    placeholderColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    borderColor: Color? = MaterialTheme.colorScheme.outline,
    borderWidth: Dp = 1.dp,
    contentScale: ContentScale = ContentScale.Crop,
    showAddIcon: Boolean = true,
    enabled: Boolean = true,
    onImageSelected: ((ByteArray) -> Unit)? = null,
) {
    val imagePicker = rememberImagePickerLauncher { result ->
        when (result) {
            is ImagePickerResult.Success -> {
                state.setImage(result.imageBytes)
                onImageSelected?.invoke(result.imageBytes)
            }
            is ImagePickerResult.Cancelled -> {
                // 취소됨 - 아무것도 하지 않음
            }
            is ImagePickerResult.Error -> {
                // 에러 발생 - 필요시 에러 처리
            }
        }
    }

    Box(
        modifier = modifier
            .size(size)
            .clip(shape)
            .background(placeholderColor)
            .then(
                if (borderColor != null) {
                    Modifier.border(borderWidth, borderColor, shape)
                } else {
                    Modifier
                }
            )
            .then(
                if (enabled) {
                    Modifier.clickable { imagePicker.launch() }
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.Center,
    ) {
        when {
            state.imageBytes != null -> {
                AsyncImage(
                    model = state.imageBytes,
                    contentDescription = "Selected photo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = contentScale,
                )
            }

            state.imageUrl != null -> {
                AsyncImage(
                    model = state.imageUrl,
                    contentDescription = "Photo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = contentScale,
                    onState = { painterState ->
                        state.setLoading(painterState is AsyncImagePainter.State.Loading)
                    },
                )
            }

            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.size(size / 3),
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            else -> {
                // 플레이스홀더
                Icon(
                    imageVector = if (showAddIcon) Icons.Default.Add else Icons.Default.Person,
                    contentDescription = "Add photo",
                    modifier = Modifier.size(size / 3),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

/**
 * 읽기 전용 PhotoBox (클릭 불가)
 */
@Composable
fun PhotoBoxReadOnly(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    size: Dp = 120.dp,
    shape: Shape = CircleShape,
    placeholderColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    borderColor: Color? = null,
    borderWidth: Dp = 1.dp,
    contentScale: ContentScale = ContentScale.Crop,
    placeholder: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "No photo",
            modifier = Modifier.size(size / 3),
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    },
) {
    var isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .size(size)
            .clip(shape)
            .background(placeholderColor)
            .then(
                if (borderColor != null) {
                    Modifier.border(borderWidth, borderColor, shape)
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.Center,
    ) {
        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.size(size / 3),
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            imageUrl != null -> {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Photo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = contentScale,
                    onState = { painterState ->
                        val newLoadingState = painterState is AsyncImagePainter.State.Loading
                        if (isLoading != newLoadingState) {
                            isLoading = newLoadingState
                        }
                    },
                )
            }

            else -> {
                placeholder()
            }
        }
    }
}
