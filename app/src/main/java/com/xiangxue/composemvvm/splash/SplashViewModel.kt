package com.xiangxue.composemvvm.splash

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.collections.HashMap

class SplashViewModel : ViewModel() {
    private val Tag = "SplashViewModel"
    private val composableMap = HashMap<Int, ISplashComposableTask>()
    open val mCurrentSplashTaskIndex = mutableStateOf(0)

    init {
        ServiceLoader.load(ISplashComposableTask::class.java).forEach { service ->
            if (composableMap[service.index] != null) {
                throw DuplicateIndexException(composableMap[service.index]!!)
            }
            composableMap[service.index] = service
        }
    }

    private fun getComposable(index: Int): ISplashComposableTask? {
        return composableMap[index]
    }

    @Composable
    fun executeCurrentTask() {
        if (getComposable(mCurrentSplashTaskIndex.value) != null) {
            Log.d(
                Tag,
                "Execute No.${mCurrentSplashTaskIndex.value} task ${
                    getComposable(mCurrentSplashTaskIndex.value).toString()
                }"
            )
            getComposable(mCurrentSplashTaskIndex.value)!!.SplashComposableItem()
        }
    }

    /*
    mCurrentSplashTaskIndex.value implementation -> SnapshotMutableStateImpl

    // SnapShotState.kt
    internal open class SnapshotMutableStateImpl<T>(
    value: T,
    override val policy: SnapshotMutationPolicy<T>
    ) : StateObject, SnapshotMutableState<T> {
        @Suppress("UNCHECKED_CAST")
        override var value: T
            get() = next.readable(this).value
            set(value) = next.withCurrent {
                if (!policy.equivalent(it.value, value)) {
                    next.overwritable(this, it) { this.value = value }
                }
            }


    //Snapshot.kt
    @PublishedApi
    internal fun notifyWrite(snapshot: Snapshot, state: StateObject) {
        snapshot.writeObserver?.invoke(state)
    }
     */

    @Composable
    fun findNextTask() {
        LaunchedEffect(key1 = mCurrentSplashTaskIndex.value, block = {
            Log.d(Tag, "Find out next available task.")
            for (i in (mCurrentSplashTaskIndex.value + 1)..100) {
                Log.d(Tag, "Try task index $i")
                if (getComposable(i) != null) {
                    Log.d(Tag, "Got next available task $i ${getComposable(i).toString()}")
                    mCurrentSplashTaskIndex.value = i
                    break
                }
            }
        })

    }

    fun exitApp() {
        System.exit(0)
    }
}