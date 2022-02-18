package com.xiangxue.base.compose.composablemanager

import android.util.Log
import androidx.compose.runtime.Composable
import com.xiangxue.base.compose.viewmodel.IBaseViewModel
import java.util.*
import kotlin.collections.HashMap

@Composable
inline fun <reified T> ComposableItem(item: T) where T : IBaseViewModel {
    ComposableServiceManager.getComposable(item!!::class.java.name)?.also { service ->
        service.ComposableItem(item)
    } ?: run {
        Log.e("ComposableService", "Error !" + item!!::class.java.name + " not register")
    }
}

object ComposableServiceManager {

    private val composableMap = HashMap<String, IComposableService<*>>()

    fun getComposable(key: String): IComposableService<*>? {
        return composableMap[key]
    }

    fun collectServices() {
        ServiceLoader.load(IComposableService::class.java).forEach { service ->
            composableMap[service.type] = service
        }
    }
}