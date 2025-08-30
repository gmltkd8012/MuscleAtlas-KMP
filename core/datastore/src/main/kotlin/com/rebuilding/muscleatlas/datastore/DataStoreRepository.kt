package com.rebuilding.muscleatlas.datastore

import com.rebuilding.muscleatlas.datastore.extension.nullToDefault
import com.rebuilding.muscleatlas.util.DataStoreUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface DataStoreRepository {

    val currentAppTheme: Flow<String>

    suspend fun updateAppTheme(appTheme: String)
}