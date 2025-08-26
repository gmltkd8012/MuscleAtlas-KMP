package com.rebuilding.muscleatlas.setting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rebuilding.muscleatlas.datastore.DataStoreRepository
import com.rebuilding.muscleatlas.model.AppTheme
import com.rebuilding.muscleatlas.ui.base.StateReducerViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : StateReducerViewModel<ThemeState, Nothing>(ThemeState()) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.currentAppTheme.collect { mode ->
                reduceState { copy(mode = mode) }
            }
        }
    }

    fun setTheme(mode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.setCurrentAppTheme(mode)
        }
    }
}

data class ThemeState(
    val mode: String = AppTheme.Light.id
)