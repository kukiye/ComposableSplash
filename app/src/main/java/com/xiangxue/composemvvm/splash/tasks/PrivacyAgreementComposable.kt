package com.xiangxue.composemvvm.splash.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.google.auto.service.AutoService
import com.xiangxue.composemvvm.splash.ISplashComposableTask
import com.xiangxue.composemvvm.splash.SplashViewModel

@AutoService(ISplashComposableTask::class)
class PrivacyAgreementComposable : ISplashComposableTask {
    override val content: @Composable (viewModel: SplashViewModel) -> Unit = { viewModel ->
        PrivacyAgreementSection(viewModel = viewModel)
    }

    override val index: Int
        get() = 1
}

@Composable
fun PrivacyAgreementSection(viewModel: SplashViewModel) {
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
                Text(text = "隐私协议")
            },
            text = {
                Text(buildAnnotatedString {
                    withStyle(style = ParagraphStyle(lineHeight = 30.sp)) {
                        withStyle(style = SpanStyle(color = Color.Black)) {
                            append("    感谢您对本公司的支持!本公司非常重视您的个人信息和隐私保护。为了更好地" +
                                    "保障您的个人权益，在您使用我们的产品前，请务必审慎阅读" +
                                    "内的所有条款，您点击“同意并继续”的行为即" +
                                    "表示您已阅读完毕并同意以上协议的全部内容。" +
                                    "\n    如您同意以上协议内容，请点击“同意”，开始使用我们的产品和服务!")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = Color.Blue
                            )
                        ) {
                            append("《隐私政策》")
                        }
                        withStyle(style = SpanStyle(color = Color.Black)) {
                            append("和")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = Color.Blue
                            )
                        ) {
                            append("《用户协议》")
                        }
                        withStyle(style = SpanStyle(color = Color.Black)) {
                            append("内的所有条款，您点击“同意并继续”的行为即" +
                                    "表示您已阅读完毕并同意以上协议的全部内容。" +
                                    "\n    如您同意以上协议内容，请点击“同意”，开始使用我们的产品和服务!")
                        }
                    }
                }, lineHeight = 10.sp)
            },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                    }) {
                    Text("同意")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                        viewModel.exitApp()
                    }) {
                    Text("不同意")
                }
            },
            modifier = Modifier.fillMaxWidth(fraction = 0.90f)
        )
    } else {
        viewModel.findNextTask()
    }
}