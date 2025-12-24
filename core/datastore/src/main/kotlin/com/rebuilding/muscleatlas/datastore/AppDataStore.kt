package com.rebuilding.muscleatlas.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.rebuilding.muscleatlas.util.DataStoreUtils

const val DATA_STORE_NAME = "muscle_atlas_datastore"

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

class AppDataStore {

    object Keys {
        val CURRENT_THEME = stringPreferencesKey(DataStoreUtils.KeyConst.CURRENT_THEME)
    }

}