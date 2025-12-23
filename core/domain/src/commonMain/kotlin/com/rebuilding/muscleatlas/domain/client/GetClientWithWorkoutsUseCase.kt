package com.rebuilding.muscleatlas.domain.client

import com.rebuilding.muscleatlas.data.client.ClientRepository
import com.rebuilding.muscleatlas.data.workout.WorkoutRepository
import com.rebuilding.muscleatlas.model.ClientWithWorkout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetClientWithWorkoutsUseCase @Inject constructor(
    private val clientRepository: ClientRepository,
    private val workoutRepository: WorkoutRepository
) {

    suspend operator fun invoke(clientId: String): Flow<ClientWithWorkout> =
        combine(
            clientRepository.getClientById(clientId),
            workoutRepository.getAllWorkouts()
        ) { client, workouts ->
            ClientWithWorkout(
                client = client,
                workoutList = workouts
            )
        }
}