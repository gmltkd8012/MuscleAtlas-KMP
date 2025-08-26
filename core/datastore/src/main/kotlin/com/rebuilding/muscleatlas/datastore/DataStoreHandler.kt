package com.rebuilding.muscleatlas.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.rebuilding.muscleatlas.datastore.extension.findCurrentStream
import com.rebuilding.muscleatlas.datastore.extension.setData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class DataStoreHandler @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    val currentTheme = AppDataStore.Keys.CURRENT_THEME.findCurrentStream(dataStore)

    suspend fun setCurrentTheme(value: String) =
        AppDataStore.Keys.CURRENT_THEME.setData(dataStore, value)

    private suspend fun runThrowException(
        scope: CoroutineContext = Dispatchers.IO + SupervisorJob(),
        block: suspend () -> Unit,
    ) {
        try {
            block.invoke()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}