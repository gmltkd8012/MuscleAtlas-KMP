package com.rebuilding.muscleatlas.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.rebuilding.muscleatlas.datastore.extension.nullToDefault
import com.rebuilding.muscleatlas.util.DataStoreUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class DataStoreRepositoryImpl @Inject constructor(
    @Named("settings") private val dataStore: DataStore<Preferences>
) : DataStoreRepository {

    override val currentAppTheme = dataStore.data.map { preferences ->
        preferences[AppDataStore.Keys.CURRENT_THEME].nullToDefault(DataStoreUtils.ValueConst.LIGHT_THEME)
    }

    override suspend fun updateAppTheme(appTheme: String) {
        dataStore.edit { preferences ->
            preferences[AppDataStore.Keys.CURRENT_THEME] = appTheme
        }
    }
}