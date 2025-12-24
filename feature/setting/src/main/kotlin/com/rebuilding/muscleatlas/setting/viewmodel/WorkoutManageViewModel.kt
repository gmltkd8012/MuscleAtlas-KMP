package com.rebuilding.muscleatlas.setting.viewmodel

import androidx.lifecycle.viewModelScope
import com.rebuilding.muscleatlas.domain.workout.GetWorkoutUseCase
import com.rebuilding.muscleatlas.model.state.WorkoutManageState
import com.rebuilding.muscleatlas.ui.base.StateReducerViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutManageViewModel @Inject constructor(
    private val getWorkoutUseCase: GetWorkoutUseCase,
): StateReducerViewModel<WorkoutManageState, Nothing>(WorkoutManageState()) {


    init {
        viewModelScope.launch(Dispatchers.IO) {
            getWorkoutUseCase().collect { workouts ->
                reduceState {
                    copy(workouts = workouts)
                }
            }
        }
    }
}