package com.rebuilding.muscleatlas.setting.viewmodel

import androidx.lifecycle.viewModelScope
import com.rebuilding.muscleatlas.domain.movement.GetMovementUseCase
import com.rebuilding.muscleatlas.domain.movement.UpdateMovementUseCase
import com.rebuilding.muscleatlas.domain.workout.GetWorkoutUseCase
import com.rebuilding.muscleatlas.domain.workout.UpdateWorkoutUseCase
import com.rebuilding.muscleatlas.model.MovementData
import com.rebuilding.muscleatlas.model.WorkoutData
import com.rebuilding.muscleatlas.model.state.WorkoutAddState
import com.rebuilding.muscleatlas.ui.base.StateReducerViewModel
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
) : StateReducerViewModel<WorkoutAddState, Nothing>(WorkoutAddState()) {

    suspend fun getMovement(workoutId: String?) {
        withContext(Dispatchers.IO) {
            getMovementUseCase(workoutId).collect { movement ->
                reduceState {
                    copy(joinMovementList = movement)
                }
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

}