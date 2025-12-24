package com.rebuilding.muscleatlas.setting.viewmodel

import android.util.Log
import androidx.compose.ui.unit.Constraints
import androidx.lifecycle.viewModelScope
import com.rebuilding.muscleatlas.domain.movement.UpdateMovementUseCase
import com.rebuilding.muscleatlas.domain.workout.GetWorkoutUseCase
import com.rebuilding.muscleatlas.domain.workout.GetWorkoutWithMovementUseCase
import com.rebuilding.muscleatlas.domain.workout.UpdateWorkoutUseCase
import com.rebuilding.muscleatlas.model.Contraction
import com.rebuilding.muscleatlas.model.Movement
import com.rebuilding.muscleatlas.model.MovementData
import com.rebuilding.muscleatlas.model.WorkoutData
import com.rebuilding.muscleatlas.model.state.ContractionTypeList
import com.rebuilding.muscleatlas.model.state.WorkoutAddState
import com.rebuilding.muscleatlas.model.state.deleteWith
import com.rebuilding.muscleatlas.model.state.initWith
import com.rebuilding.muscleatlas.model.state.saveWith
import com.rebuilding.muscleatlas.model.state.updateWith
import com.rebuilding.muscleatlas.ui.base.StateReducerViewModel
import com.rebuilding.muscleatlas.util.MovementUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WorkoutAddViewModel @Inject constructor(
    private val updateWorkoutUseCase: UpdateWorkoutUseCase,
    private val updateMovementUseCase: UpdateMovementUseCase,
    private val getWorkoutWithMovementUseCase: GetWorkoutWithMovementUseCase,
) : StateReducerViewModel<WorkoutAddState, WorkoutAddSdieEffect>(WorkoutAddState()) {

    suspend fun initData(workoutId: String?) {
        withContext(Dispatchers.IO) {
            getWorkoutWithMovementUseCase(workoutId ?: "").collect { data ->
                reduceState {
                    copy(
                        workout = data.workoutData,
                        concentric = state.value.concentric.initWith(data.concentricMovementList),
                        eccentric = state.value.eccentric.initWith(data.eccentricMovementList),
                    )
                }
            }
        }
    }

    fun updateMovementsWithWorkout(
        workoutData: WorkoutData,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            launch {
                updateWorkoutUseCase(workoutData)
            }
            launch {
                updateMovementUseCase(
                    workoutId = workoutData.id,
                    movements = state.value.eccentric.saveWith() + state.value.concentric.saveWith()
                )
            }
        }
    }

    fun showMovementBottomSheet(movement: MovementData?) {
        viewModelScope.launch {
            sendSideEffect(WorkoutAddSdieEffect.ShowMovementAddBottomSheet(movement))
        }
    }

    fun showMovementDeleteDialog(movement: MovementData) {
        viewModelScope.launch {
            sendSideEffect(WorkoutAddSdieEffect.ShowMovementDeleteDialog(movement))
        }
    }

    fun updateMovementUI(movement: MovementData) {
        viewModelScope.launch(Dispatchers.IO) {
            when (movement.contraction) {
                Contraction.Concentric.value ->
                    reduceState {
                        copy(concentric = state.value.concentric.updateWith(movement))
                    }

                Contraction.Eccentric.value ->
                    reduceState {
                        copy(eccentric = state.value.eccentric.updateWith(movement))
                    }
            }
        }
    }

    fun deleteMovementUI(movement: MovementData?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (movement == null) return@launch

            when (movement.contraction) {
                Contraction.Concentric.value ->
                    reduceState {
                        copy(concentric = state.value.concentric.deleteWith(movement))
                    }

                Contraction.Eccentric.value ->
                    reduceState {
                        copy(eccentric = state.value.eccentric.deleteWith(movement))
                    }
            }
        }
    }
}

sealed interface WorkoutAddSdieEffect {
    data class ShowMovementAddBottomSheet(val movement: MovementData? = null) : WorkoutAddSdieEffect
    data class ShowMovementDeleteDialog(val movement: MovementData) : WorkoutAddSdieEffect
}
