package com.rebuilding.muscleatlas.client.viewmodel

import androidx.lifecycle.viewModelScope
import com.rebuilding.muscleatlas.datastore.DataStoreRepository
import com.rebuilding.muscleatlas.domain.client.GetClientWithWorkoutsUseCase
import com.rebuilding.muscleatlas.model.ClientWithWorkout
import com.rebuilding.muscleatlas.model.WorkoutData
import com.rebuilding.muscleatlas.model.state.ClientDetailState
import com.rebuilding.muscleatlas.model.state.ThemeState
import com.rebuilding.muscleatlas.ui.base.StateReducerViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ClientViewModel @Inject constructor(
    private val getClientWithWorkoutsUseCase: GetClientWithWorkoutsUseCase,
): StateReducerViewModel<ClientDetailState, Nothing>(ClientDetailState()) {


    suspend fun getClientWithWorkouts(clientId: String) {
        withContext(Dispatchers.IO) {
            getClientWithWorkoutsUseCase(clientId).collect {
                reduceState {
                    copy(
                        client = it.client,
                        workoutList = it.workoutList
                    )
                }
            }
        }
    }

    fun selectedWorkout(workout: WorkoutData) {
        viewModelScope.launch() {
            reduceState {
                copy(
                    selectedWorkout = workout
                )
            }
        }
    }
}