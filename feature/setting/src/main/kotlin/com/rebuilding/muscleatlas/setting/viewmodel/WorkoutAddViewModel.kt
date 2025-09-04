package com.rebuilding.muscleatlas.setting.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.rebuilding.muscleatlas.domain.movement.GetMovementUseCase
import com.rebuilding.muscleatlas.domain.movement.UpdateMovementUseCase
import com.rebuilding.muscleatlas.domain.workout.GetWorkoutUseCase
import com.rebuilding.muscleatlas.domain.workout.GetWorkoutWithMovementUseCase
import com.rebuilding.muscleatlas.domain.workout.UpdateWorkoutUseCase
import com.rebuilding.muscleatlas.model.Movement
import com.rebuilding.muscleatlas.model.MovementData
import com.rebuilding.muscleatlas.model.WorkoutData
import com.rebuilding.muscleatlas.model.state.WorkoutAddState
import com.rebuilding.muscleatlas.ui.base.StateReducerViewModel
import com.rebuilding.muscleatlas.util.MovementUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WorkoutAddViewModel @Inject constructor(
    private val getMovementUseCase: GetMovementUseCase,
    private val updateWorkoutUseCase: UpdateWorkoutUseCase,
    private val updateMovementUseCase: UpdateMovementUseCase,
    private val getWorkoutWithMovementUseCase: GetWorkoutWithMovementUseCase,
) : StateReducerViewModel<WorkoutAddState, WorkoutAddSdieEffect>(WorkoutAddState()) {

    suspend fun initData(workoutId: String?) {
        withContext(Dispatchers.IO) {
            getWorkoutWithMovementUseCase(workoutId ?: "").collect { data ->
                initWorkoutData(data.workoutData)
                data.movementList.map { updateMovementUI(it) }
            }
        }
    }

    fun updateWorkout(workoutData: WorkoutData) {
        viewModelScope.launch(Dispatchers.IO) {
            updateWorkoutUseCase(workoutData)
        }
    }

    fun updateMovements(movementData: List<MovementData>) {
        viewModelScope.launch(Dispatchers.IO) {
            updateMovementUseCase(movementData)
        }
    }

    fun showMovementBottomSheet(movement: MovementData?) {
        viewModelScope.launch {
            sendSideEffect(WorkoutAddSdieEffect.ShowMovementAddBottomSheet(movement))
        }
    }

    fun initWorkoutData(workoutData: WorkoutData) {
        viewModelScope.launch {
            reduceState {
                copy(
                    workout = workoutData
                )
            }
        }
    }

    fun updateMovementUI(movement: MovementData) {
        viewModelScope.launch {
            when(movement.type) {
                MovementUtils.TYPE_JOIN_MOVEMENT -> {
                    reduceState {
                        copy(
                            joinMovementList = state.value.joinMovementList + movement
                        )
                    }
                }
                MovementUtils.TYPE_STABILIZATION_MECHANISM -> {
                    reduceState {
                        copy(
                            stabilizationMechanismList = state.value.stabilizationMechanismList + movement
                        )
                    }
                }
                MovementUtils.TYPE_MUSCULAR_RELATION -> {
                    reduceState {
                        copy(
                            neuromuscularRelationList = state.value.neuromuscularRelationList + movement
                        )
                    }
                }
            }
        }
    }
}

sealed interface WorkoutAddSdieEffect {
    data class ShowMovementAddBottomSheet(val movement: MovementData? = null) : WorkoutAddSdieEffect
}
