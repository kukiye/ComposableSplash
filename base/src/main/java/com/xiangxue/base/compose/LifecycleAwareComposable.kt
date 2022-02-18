package com.xiangxue.base.compose

import androidx.compose.runtime.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

object LifecycleAwareComposable {
    @Composable
    fun Lifecycle.observeAsSate(): State<Lifecycle.Event> {
        val state = remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
        DisposableEffect(this) {
            val observer = LifecycleEventObserver { _, event ->
                state.value = event
            }
            this@observeAsSate.addObserver(observer)
            onDispose {
                this@observeAsSate.removeObserver(observer)
            }
        }
        return state
    }
}