package com.xiangxue.base.mvvm.view

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.xiangxue.base.mvvm.viewmodel.MvvmBaseViewModel
import com.xiangxue.base.mvvm.viewmodel.ViewStatus
import androidx.compose.material.Text
import androidx.compose.ui.platform.LocalContext

@Composable
fun StatusComposable(
    mvvmBaseViewModel: MvvmBaseViewModel<*, *>?,
    content: @Composable (item: MvvmBaseViewModel<*, *>?) -> Unit
) {
    when (mvvmBaseViewModel?.viewStatus?.value) {
        ViewStatus.LOADING -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Text(text = "加载中，请稍候...")
            }
        }
        ViewStatus.EMPTY -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clickable {
                        mvvmBaseViewModel.tryToRefresh()
                    }
            ) {
                Text(text = "内容为空，点击刷新。")
            }
        }
        ViewStatus.SHOW_CONTENT -> {
            content(mvvmBaseViewModel)
        }
        ViewStatus.NO_MORE_DATA -> {
            content(mvvmBaseViewModel)
            Toast.makeText(LocalContext.current, "没有更多了。", Toast.LENGTH_SHORT).show()
        }
        ViewStatus.REFRESH_ERROR -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clickable {
                        mvvmBaseViewModel.tryToRefresh()
                    }
            ) {
                Text(text = "加载失败，点击刷新。")
            }
        }
        ViewStatus.LOAD_MORE_FAILED -> {
            content(mvvmBaseViewModel)
            Toast.makeText(LocalContext.current, mvvmBaseViewModel.errorMessage, Toast.LENGTH_SHORT)
                .show()
        }
        ViewStatus.REFRESH_ERROR_AND_CACHE_LOADED -> {
            content(mvvmBaseViewModel)
            Toast.makeText(LocalContext.current, "刷新错误，现在显示的是缓存数据。", Toast.LENGTH_SHORT)
                .show()
        }
    }
}