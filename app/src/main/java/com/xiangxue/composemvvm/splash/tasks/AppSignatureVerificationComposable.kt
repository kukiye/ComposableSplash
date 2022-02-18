package com.xiangxue.composemvvm.splash.tasks

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.auto.service.AutoService
import com.xiangxue.composemvvm.splash.ISplashComposableTask
import com.xiangxue.composemvvm.splash.SplashViewModel
import com.xiangxue.composemvvm.splash.utils.SignCheck

@AutoService(ISplashComposableTask::class)
class AppSignatureVerificationComposable : ISplashComposableTask {
    override val content: @Composable (viewModel: SplashViewModel) -> Unit = { viewModel ->
        AppSignatureVerificationComposableSection(viewModel = viewModel)
    }

    override val index: Int
        get() = 0
}

//keytool -list -v -keystore allen.jks
@Composable
fun AppSignatureVerificationComposableSection(viewModel: SplashViewModel) {
    val signCheck = SignCheck(
        LocalContext.current,
        "F0:54:E9:61:A5:9A:AB:A6:02:5A:49:F0:A4:D5:DC:22:5A:15:08:43"
    )
    if (signCheck.check()) {
        viewModel.findNextTask()
    } else {
        val openDialog = remember { mutableStateOf(true) }
        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                    // button. If you want to disable that functionality, simply use an empty
                    // onCloseRequest.
                    openDialog.value = false
                    viewModel.exitApp()
                },
                title = {
                    Text(text = "警告")
                },
                text = {
                    Text(text = "您当前使用的应用非官方版本，请前往官方渠道下载正版。")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            openDialog.value = false
                            viewModel.exitApp()
                        }) {
                        Text("退出去应用商店下载")
                    }
                },
                modifier = Modifier.fillMaxWidth(fraction = 0.90f)
            )
        }
    }
}

