package com.rebuilding.muscleatlas.ui.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import com.rebuilding.muscleatlas.model.DeleteMovementDialogData
import com.rebuilding.muscleatlas.model.MovementData

typealias DeleteMovementDialogState = MutableState<DeleteMovementDialogData>

@Composable
fun <SideEffect> rememberDeleteMovementDialogState(
    initial: DeleteMovementDialogData = DeleteMovementDialogData(),
): DeleteMovementDialogState = rememberSaveable(
    saver = Saver<MutableState<DeleteMovementDialogData>, List<Any?>>(
        save = { listOf(it.value.isShown, it.value.movement) },
        restore = { list ->
            mutableStateOf(
                DeleteMovementDialogData(
                    isShown = list[0] as Boolean,
                    movement = list[1] as MovementData,
                )
            )
        }
    ),
) {
    mutableStateOf(initial)
}


val DeleteMovementDialogState.isShown get() = this.value.isShown

fun DeleteMovementDialogState.show(
    movement: MovementData,
) {
    this.value = this.value.copy(
        isShown = true,
        movement = movement
    )
}

fun DeleteMovementDialogState.hide() {
    this.value = this.value.copy(
        isShown = false,
        movement = null
    )
}