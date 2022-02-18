package com.xiangxue.news.homefragment.newslist

import android.app.Activity
import android.os.Bundle
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.savedstate.SavedStateRegistryOwner
import com.google.accompanist.pager.ExperimentalPagerApi
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.xiangxue.base.compose.composablemanager.ComposableItem
import com.xiangxue.base.compose.lazycolumn.LoadMoreListHandler
import com.xiangxue.base.mvvm.view.StatusComposable
import com.xiangxue.news.homefragment.api.Channel

@ExperimentalPagerApi
@Composable
fun NewsListComposableWithSmartRefreshLayout(isVisible: MutableState<Boolean>, channel: Channel) {
    val context = LocalContext.current as Activity
    val bundle = Bundle()

    bundle.putString(BUNDLE_KEY_PARAM_CHANNEL_ID, channel.channelId)
    bundle.putString(BUNDLE_KEY_PARAM_CHANNEL_NAME, channel.name)
    val viewModel = ViewModelProvider(
        context as ViewModelStoreOwner, SavedStateViewModelFactory(
            context.application,
            context as SavedStateRegistryOwner, bundle
        )
    )[channel.channelId + channel.name, NewsListViewModel::class.java]

    StatusComposable(mvvmBaseViewModel = viewModel) {
        val isRefreshing by viewModel.isRefreshing.collectAsState()
        // Adds view to Compose
        AndroidView(
            modifier = Modifier.fillMaxSize(), // Occupy the max size in the Compose UI tree
            factory = { context ->
                // Creates custom view
                SmartRefreshLayout(context).apply {
                    setOnRefreshListener { viewModel.tryToRefresh() }
                }
            },
            update = { view ->
                view.addView(ComposeView(view.context).apply {
                    // Dispose the Composition when the view's LifecycleOwner
                    // is destroyed
                    setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                    setContent {
                        MaterialTheme {
                            // In Compose world
                            NewsListComposableWithoutSwipeToRefresh(
                                isVisible = isVisible,
                                channel = channel
                            )
                        }
                    }
                })
                view.setRefreshHeader(ClassicsHeader(view.context))
                if (!isRefreshing) {
                    view.finishRefresh()
                }
            }
        )
    }
}

@ExperimentalPagerApi
@Composable
fun NewsListComposableWithoutSwipeToRefresh(isVisible: MutableState<Boolean>, channel: Channel) {
    val context = LocalContext.current as Activity
    val bundle = Bundle()

    bundle.putString(BUNDLE_KEY_PARAM_CHANNEL_ID, channel.channelId)
    bundle.putString(BUNDLE_KEY_PARAM_CHANNEL_NAME, channel.name)
    val viewModel = ViewModelProvider(
        context as ViewModelStoreOwner, SavedStateViewModelFactory(
            context.application,
            context as SavedStateRegistryOwner, bundle
        )
    )[channel.channelId + channel.name, NewsListViewModel::class.java]

    StatusComposable(mvvmBaseViewModel = viewModel) {
        val listState = rememberLazyListState()
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                bottom = 10.dp,
                top = 10.dp
            ),
            state = listState
        ) {
            firstVisibleItemChanged(
                viewModel = viewModel,
                isVisible = isVisible.value,
                listState.firstVisibleItemIndex
            )
            items(viewModel.dataList) {
                ComposableItem(item = it)
            }
        }

        LoadMoreListHandler(listState = listState) {
            viewModel.tryToLoadNextPage()
        }
    }
}
