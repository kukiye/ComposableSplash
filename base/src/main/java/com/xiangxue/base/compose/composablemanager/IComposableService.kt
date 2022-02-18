package com.xiangxue.base.compose.composablemanager

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import com.xiangxue.base.compose.LifecycleAwareComposable.observeAsSate
import com.xiangxue.base.compose.viewmodel.IBaseViewModel


interface IComposableService<T> {

    val content: @Composable (item: T) -> Unit

    val type: String

    @Suppress("UNCHECKED_CAST")
    @Composable
    fun ComposableItem(item: IBaseViewModel) {
        val lifeCycleState = LocalLifecycleOwner.current?.lifecycle?.observeAsSate()
        (item as? T)?.let {
            content(item)
        }

        LaunchedEffect(key1 = lifeCycleState.value, block = {
            Log.d("ComposableItem", "${item}===${lifeCycleState.value}")
            item.isResumed.value = lifeCycleState.value == Lifecycle.Event.ON_RESUME
        })
    }
}