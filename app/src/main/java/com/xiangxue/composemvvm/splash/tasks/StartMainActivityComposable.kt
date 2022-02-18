package com.xiangxue.composemvvm.splash.tasks

import android.content.Intent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.google.auto.service.AutoService
import com.xiangxue.composemvvm.main.MainActivity
import com.xiangxue.composemvvm.splash.ISplashComposableTask
import com.xiangxue.composemvvm.splash.SplashViewModel

@AutoService(ISplashComposableTask::class)
class StartMainActivityComposable : ISplashComposableTask {

    override val content: @Composable (viewModel: SplashViewModel) -> Unit = { viewModel ->
        StartMainActivitySection(viewModel = viewModel)
    }

    override val index: Int
        get() = 2
}

@Composable
fun StartMainActivitySection(viewModel: SplashViewModel) {
    LocalContext.current.startActivity(
        Intent(
            LocalContext.current,
            MainActivity::class.java
        )
    )
}
