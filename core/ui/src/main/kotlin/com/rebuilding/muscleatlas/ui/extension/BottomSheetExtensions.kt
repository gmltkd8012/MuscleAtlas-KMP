package com.rebuilding.muscleatlas.ui.extension

import android.R.attr.name
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import com.rebuilding.muscleatlas.model.Client
import com.rebuilding.muscleatlas.model.ClientBottomSheetData

typealias ClientBottomSheetState = MutableState<ClientBottomSheetData>

@Composable
fun <SideEffect> rememberClientBottomSheetState(
    initial: ClientBottomSheetData = ClientBottomSheetData(),
): ClientBottomSheetState = rememberSaveable(
    saver = Saver<MutableState<ClientBottomSheetData>, List<Any?>>(
        save = {
            listOf(it.value.isShown, it.value.imgUrl, it.value.id, it.value.name, it.value.memo, it.value.currentMills)
        },
        restore = { list ->
            mutableStateOf(
                ClientBottomSheetData(
                    isShown = list[0] as Boolean,
                    imgUrl = list[1] as String?,
                    id = list[2] as String?,
                    name = list[3] as String?,
                    memo = list[4] as String?,
                    currentMills = list[5] as Long?,
                )
            )
        }
    )
) {
    mutableStateOf(initial)
}

val ClientBottomSheetState.isShown: Boolean get() = this.value.isShown

fun ClientBottomSheetState.show(
    client: Client? = null,
) {
    this.value = this.value.copy(
        isShown = true,
        imgUrl = client?.imgUrl,
        id = client?.id,
        name = client?.name ?: "",
        memo = client?.memo ?: "",
        currentMills = client?.currentMills,
    )
}

fun ClientBottomSheetState.hide() {
    this.value = this.value.copy(
        isShown = false,
        imgUrl = null,
        id = null,
        name = null,
        memo = null,
        currentMills = null,
    )
}