package com.xiangxue.composemvvm

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.xiangxue.composemvvm.splash.SplashViewModel

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
/**
 * 利用给SplashActivity设置带有背景的SplashTheme来避免启动白屏的问题
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.post {
            setContent {
                val viewModel: SplashViewModel = viewModel()
                viewModel.executeCurrentTask()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}