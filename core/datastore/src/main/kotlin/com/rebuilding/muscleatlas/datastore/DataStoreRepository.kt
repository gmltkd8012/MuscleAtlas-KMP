package com.rebuilding.muscleatlas.datastore

import com.rebuilding.muscleatlas.datastore.extension.findCurrentStream
import com.rebuilding.muscleatlas.datastore.extension.nullToDefault
import com.rebuilding.muscleatlas.util.DataStoreUtils
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreRepository @Inject constructor(
      private val dataStoreHandler: DataStoreHandler,
) {

    val currentAppTheme = dataStoreHandler.currentTheme
        .map { it.nullToDefault(DataStoreUtils.ValueConst.LIGHT_THEME) }
        .distinctUntilChanged()

    suspend fun setCurrentAppTheme(value: String) = dataStoreHandler.setCurrentTheme(value)
}