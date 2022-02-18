package com.xiangxue.composemvvm.splash

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

interface ISplashComposableTask {
    val content: @Composable (viewModel: SplashViewModel) -> Unit
    val index: Int

    @Composable
    fun SplashComposableItem() {
        val viewModel: SplashViewModel = viewModel()
        if (viewModel.mCurrentSplashTaskIndex.value == index) {
            content(viewModel)
        }
    }
}