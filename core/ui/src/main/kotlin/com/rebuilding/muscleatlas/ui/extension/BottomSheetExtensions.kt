package com.rebuilding.muscleatlas.ui.extension

import android.R.attr.name
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import com.rebuilding.muscleatlas.model.Client
import com.rebuilding.muscleatlas.model.ClientBottomSheetData
import com.rebuilding.muscleatlas.model.Movement
import com.rebuilding.muscleatlas.model.MovementData
import com.rebuilding.muscleatlas.model.MovmentBottomSheetData

typealias ClientBottomSheetState = MutableState<ClientBottomSheetData>
typealias MovementBottomSheetState = MutableState<MovmentBottomSheetData>

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

@Composable
fun <SideEffect> rememberMovementBottomSheetState(
    initial: MovmentBottomSheetData = MovmentBottomSheetData(),
): MovementBottomSheetState = rememberSaveable(
    saver = Saver<MutableState<MovmentBottomSheetData>, List<Any?>>(
        save = {
            listOf(it.value.isShown, it.value.imgUrl, it.value.type, it.value.id, it.value.title, it.value.description, it.value.currentMills)
        },
        restore = {
            mutableStateOf(
                MovmentBottomSheetData(
                    isShown = it[0] as Boolean,
                    imgUrl = it[1] as String?,
                    type = it[2] as Int?,
                    id = it[3] as String?,
                    title = it[4] as String?,
                    description = it[5] as String?,
                    currentMills = it[6] as Long
                )
            )
        }
    )
) {
    mutableStateOf(initial)
}

@get:JvmName("clientShownState")
val ClientBottomSheetState.isShown: Boolean get() = this.value.isShown

@get:JvmName("movementShownState")
val MovementBottomSheetState.isShown: Boolean get() = this.value.isShown

@JvmName("clientShow")
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

@JvmName("movmentShow")
fun MovementBottomSheetState.show(
    movement: MovementData? = null,
) {
    this.value = this.value.copy(
        isShown = true,
        imgUrl = movement?.imgUrl,
        type = movement?.type,
        id = movement?.id,
        title = movement?.title ?: "",
        description = movement?.description ?: "",
        currentMills = movement?.currentMills,
    )
}

@JvmName("clientHide")
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

@JvmName("movementHide")
fun MovementBottomSheetState.hide() {
    this.value = this.value.copy(
        isShown = false,
        imgUrl = null,
        type = null,
        id = null,
        title = null,
        description = null,
        currentMills = null,
    )
}