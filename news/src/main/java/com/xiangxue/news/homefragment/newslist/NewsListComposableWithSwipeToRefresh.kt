package com.xiangxue.news.homefragment.newslist

import android.app.Activity
import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.savedstate.SavedStateRegistryOwner
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.xiangxue.base.compose.composablemanager.ComposableItem
import com.xiangxue.base.compose.lazycolumn.LoadMoreListHandler
import com.xiangxue.base.mvvm.view.StatusComposable
import com.xiangxue.base.mvvm.viewmodel.MvvmBaseViewModel
import com.xiangxue.news.homefragment.api.Channel
import com.xiangxue.news.homefragment.newslist.composables.title.TitleViewModel
import com.xiangxue.news.homefragment.newslist.composables.titlepicture.TitlePictureViewModel
import com.xiangxue.news.homefragment.newslist.utils.SwipeToRefresh

@ExperimentalPagerApi
@Composable
fun NewsListComposableWithSwipeToRefresh(isVisible: MutableState<Boolean>, channel: Channel) {
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
        val isRefreshing by viewModel.isRefreshing.collectAsState()

        SwipeToRefresh(
            isRefreshing = isRefreshing,
            onRefresh = { viewModel.tryToRefresh() },
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize().background(color = Color.White),
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
}
