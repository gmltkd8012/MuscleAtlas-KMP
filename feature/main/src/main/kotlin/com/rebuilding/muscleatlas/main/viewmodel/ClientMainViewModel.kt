package com.rebuilding.muscleatlas.main.viewmodel

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rebuilding.muscleatlas.domain.client.GetClientUseCase
import com.rebuilding.muscleatlas.domain.client.UpdateClientUseCase
import com.rebuilding.muscleatlas.model.Client
import com.rebuilding.muscleatlas.model.state.ClientState
import com.rebuilding.muscleatlas.ui.base.StateReducerViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientMainViewModel @Inject constructor(
    private val getClientsUseCase: GetClientUseCase,
    private val updateClientUseCase: UpdateClientUseCase
) : StateReducerViewModel<ClientState, Nothing>(ClientState()) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getClientsUseCase().collect { clients ->
                reduceState {
                    copy(clients = clients)
                }
            }
        }
    }

    fun updateClient(client: Client) {
        viewModelScope.launch(Dispatchers.IO) {
            updateClientUseCase(client)
        }
    }
}