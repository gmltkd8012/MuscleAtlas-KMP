package com.rebuilding.muscleatlas.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.rebuilding.muscleatlas.datastore.DataStoreRepository
import com.rebuilding.muscleatlas.model.state.ThemeState
import com.rebuilding.muscleatlas.ui.base.StateReducerViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : StateReducerViewModel<ThemeState, Nothing>(ThemeState()) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.currentAppTheme.collect { mode ->
                reduceState { copy(mode = mode) }
            }
        }
    }
}