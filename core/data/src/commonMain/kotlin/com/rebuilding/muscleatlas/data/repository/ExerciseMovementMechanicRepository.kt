package com.rebuilding.muscleatlas.data.repository

import com.rebuilding.muscleatlas.data.model.ExerciseMovementMechanic
import com.rebuilding.muscleatlas.data.model.ExerciseMovementMechanicInsert
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

interface ExerciseMovementMechanicRepository {
    /**
     * 특정 운동의 Movement Mechanic 조회
     */
    fun getMovementMechanics(exerciseId: String): Flow<List<ExerciseMovementMechanic>>

    /**
     * Movement Mechanic 일괄 추가
     */
    suspend fun insertMovementMechanics(mechanics: List<ExerciseMovementMechanicInsert>)

    /**
     * Movement Mechanic 업데이트
     */
    suspend fun updateMovementMechanic(mechanic: ExerciseMovementMechanic)

    /**
     * Movement Mechanic 삭제
     */
    suspend fun deleteMovementMechanic(mechanicId: String)
}

class ExerciseMovementMechanicRepositoryImpl(
    private val supabaseClient: SupabaseClient,
    private val ioDispatcher: CoroutineDispatcher,
) : ExerciseMovementMechanicRepository {

    companion object {
        private const val TABLE = "exercise_movement_mechanics"
    }

    override fun getMovementMechanics(exerciseId: String): Flow<List<ExerciseMovementMechanic>> = flow {
        val mechanics = supabaseClient
            .from(TABLE)
            .select {
                filter {
                    eq("exercise_id", exerciseId)
                }
                order("card_type", Order.ASCENDING)
                order("display_order", Order.ASCENDING)
            }
            .decodeList<ExerciseMovementMechanic>()
        emit(mechanics)
    }.flowOn(ioDispatcher)

    override suspend fun insertMovementMechanics(mechanics: List<ExerciseMovementMechanicInsert>) {
        withContext(ioDispatcher) {
            supabaseClient
                .from(TABLE)
                .insert(mechanics)
        }
    }

    override suspend fun updateMovementMechanic(mechanic: ExerciseMovementMechanic) {
        withContext(ioDispatcher) {
            supabaseClient
                .from(TABLE)
                .update(mechanic) {
                    filter {
                        eq("id", mechanic.id)
                    }
                }
        }
    }

    override suspend fun deleteMovementMechanic(mechanicId: String) {
        withContext(ioDispatcher) {
            supabaseClient
                .from(TABLE)
                .delete {
                    filter {
                        eq("id", mechanicId)
                    }
                }
        }
    }
}
