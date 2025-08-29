package com.rebuilding.muscleatlas.viewmodel

import androidx.lifecycle.ViewModel
import com.rebuilding.muscleatlas.datastore.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    dataStoreRepository: DataStoreRepository,
): ViewModel() {

    val isDarkTheme = dataStoreRepository.currentAppTheme
}