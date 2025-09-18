package com.rebuilding.muscleatlas.ui.extension

import android.R.attr.name
import android.R.attr.type
import android.net.Uri
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
                    imgUrl = list[1] as Uri?,
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
            listOf(
                it.value.isShown,
                it.value.id,
                it.value.workoutId,
                it.value.imgUrl,
                it.value.contraction,
                it.value.type,
                it.value.title,
                it.value.description,
                it.value.currentMills
            )
        },
        restore = {
            mutableStateOf(
                MovmentBottomSheetData(
                    isShown = it[0] as Boolean,
                    id = it[1] as String?,
                    workoutId = it[2] as String?,
                    imgUrl = it[3] as Uri?,
                    type = it[4] as Int?,
                    title = it[5] as String?,
                    description = it[6] as String?,
                    currentMills = it[7] as Long
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
        id = movement?.id,
        workoutId = movement?.workoutId,
        imgUrl = movement?.imgUrl,
        contraction = movement?.contraction,
        type = movement?.type,
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
        id = null,
        workoutId = null,
        imgUrl = null,
        contraction = null,
        type = null,
        title = null,
        description = null,
        currentMills = null,
    )
}