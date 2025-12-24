package com.rebuilding.muscleatlas.client.viewmodel

import android.R.attr.data
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rebuilding.muscleatlas.datastore.DataStoreRepository
import com.rebuilding.muscleatlas.domain.client.GetClientWithWorkoutsUseCase
import com.rebuilding.muscleatlas.domain.clientmovement.GetClientMovementUseCase
import com.rebuilding.muscleatlas.domain.clientmovement.UpdateClientMovementUseCase
import com.rebuilding.muscleatlas.domain.movement.GetFilteredMovementsByWorkoutUseCase
import com.rebuilding.muscleatlas.domain.workout.GetWorkoutWithMovementUseCase
import com.rebuilding.muscleatlas.model.ClientMovementData
import com.rebuilding.muscleatlas.model.ClientWithWorkout
import com.rebuilding.muscleatlas.model.MovementData
import com.rebuilding.muscleatlas.model.WorkoutData
import com.rebuilding.muscleatlas.model.state.ClientDetailState
import com.rebuilding.muscleatlas.model.state.ThemeState
import com.rebuilding.muscleatlas.model.state.initWith
import com.rebuilding.muscleatlas.model.state.initWithClientMovement
import com.rebuilding.muscleatlas.ui.base.StateReducerViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class ClientViewModel @Inject constructor(
    private val getClientMovementUseCase: GetClientMovementUseCase,
    private val getClientWithWorkoutsUseCase: GetClientWithWorkoutsUseCase,
    private val updateClientMovementUseCase: UpdateClientMovementUseCase,
): StateReducerViewModel<ClientDetailState, ClientSideEffect>(ClientDetailState()) {


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

    fun selectedWorkout(clientId: String, workout: WorkoutData) {
        viewModelScope.launch(Dispatchers.IO) {
            if (workout == state.value.selectedWorkout) return@launch

            getClientMovementUseCase(clientId, workout.id)
                .collect { movements ->
                    reduceState {
                        copy(
                            selectedWorkout = workout,
                            concentric = state.value.concentric.initWithClientMovement(movements.concentricMovementList),
                            eccentric = state.value.eccentric.initWithClientMovement(movements.eccentricMovementList),
                        )
                    }
                }
        }
    }

    fun updateClientMovement(clientMovement: ClientMovementData) {
        Log.e("heesang", "눌림 ?")
        viewModelScope.launch(Dispatchers.IO) {
            updateClientMovementUseCase(
                clientMovement.clientId,
                clientMovement.movementId,
                clientMovement.isCompleted
            )
        }
    }

    fun showMovementDetailBottomSheet(movement: MovementData) {
        viewModelScope.launch() {
            sendSideEffect(ClientSideEffect.ShowMovementDetailBottomSheet(
                movement = movement,
            ))
        }
    }
}

sealed interface ClientSideEffect {
    data class ShowMovementDetailBottomSheet(val movement: MovementData): ClientSideEffect
}