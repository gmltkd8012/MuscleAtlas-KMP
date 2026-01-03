package com.rebuilding.muscleatlas.data.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Koin qualifier for Coroutine Dispatchers
 * Hilt의 @IoDispatcher, @DefaultDispatcher 와 동일한 역할
 */
object DispatcherQualifier {
    val Io: Qualifier = named("IoDispatcher")
    val Default: Qualifier = named("DefaultDispatcher")
}

val dispatcherModule = module {
    single<CoroutineDispatcher>(DispatcherQualifier.Io) { Dispatchers.Default }  // IO는 KMP에서 Default로 대체
    single<CoroutineDispatcher>(DispatcherQualifier.Default) { Dispatchers.Default }
}
