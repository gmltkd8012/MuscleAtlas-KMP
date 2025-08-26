package com.rebuilding.muscleatlas.ui.base

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

abstract class StateReducerViewModel<State, SideEffect>(initialState: State) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler() { _, throwable ->
        Log.e("", "[ExceptionHandler] -> $throwable")
    }

    val exceptionScope: CoroutineScope by lazy { viewModelScope + exceptionHandler }

    open val isDebug = true

    private val reduceChannel = Channel<State.() -> State>()


    protected suspend fun reduceState(reduce: State.() -> State) {
        reduceChannel.send(reduce)
    }

    val state: StateFlow<State> = reduceChannel.receiveAsFlow()
        .runningFold(initialState, ::reduce)
        .onEach {  }
        .stateIn(viewModelScope, SharingStarted.Eagerly, initialState)

    private fun reduce(currentState: State, reduce: State.() -> State) = currentState.reduce()


    // 발생하는 SideEffect 를 담는 Channel 생성
    private val _sideEffectChannel = Channel<SideEffect>(
        capacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        onUndeliveredElement = {
            if (isDebug) {}// 로그
        }
    )

    // SideEffect 채널을 Flow 로 수신
    private val sideEffectFlow = _sideEffectChannel.receiveAsFlow()

    // 채널에 SideEffect 송신
    protected suspend fun sendSideEffect(sideEffect: SideEffect) {
        _sideEffectChannel.send(sideEffect)
    }

    // 실제 Composable 함수 내부에서 SideEffect 채널에서 발생하는 이벤트들을 수신하여 동작하는 주체
    @Composable
    fun collectSideEffect(
        lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
        consumeSideEffect: (suspend (sideEffect: SideEffect) -> Unit)
    ) {
        val sideEffectFlow = sideEffectFlow
        val lifecycleOwner = LocalLifecycleOwner.current

        val sideEffectConsumer by rememberUpdatedState(consumeSideEffect)

        LaunchedEffect(sideEffectFlow, lifecycleOwner) {
            lifecycleOwner.lifecycle.repeatOnLifecycle(lifecycleState) {
                sideEffectFlow.collect {
                    launch {
                        sideEffectConsumer(it)
                    }
                }
            }
        }
    }
}