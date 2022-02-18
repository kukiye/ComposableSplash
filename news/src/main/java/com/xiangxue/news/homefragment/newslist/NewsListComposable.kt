package com.xiangxue.news.homefragment.newslist

import android.app.Activity
import android.os.Bundle
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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

const val BUNDLE_KEY_PARAM_CHANNEL_ID = "bundle_key_param_channel_id"
const val BUNDLE_KEY_PARAM_CHANNEL_NAME = "bundle_key_param_channel_name"

@ExperimentalPagerApi
@Composable
fun NewsListComposable(isVisible: MutableState<Boolean>, channel: Channel) {
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

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { viewModel.tryToRefresh() },
        ) {
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
}

fun firstVisibleItemChanged(
    viewModel: MvvmBaseViewModel<*, *>,
    isVisible: Boolean,
    firstItemIndex: Int
) {
    viewModel.dataList.forEach {
        if (it is TitleViewModel) {
            it.isFirstVisible.value = false
        } else if (it is TitlePictureViewModel) {
            it.isFirstVisible.value = false
        }
    }
    if (isVisible) {
        val firstVisibleItem = viewModel.dataList[firstItemIndex]
        if (firstVisibleItem is TitleViewModel) {
            firstVisibleItem.isFirstVisible.value = true
        } else if (firstVisibleItem is TitlePictureViewModel) {
            firstVisibleItem.isFirstVisible.value = true
        }
    }
}

@Composable
fun observeListState(
    isVisible: MutableState<Boolean>,
    listState: LazyListState,
    callback: () -> Unit
) {
    LaunchedEffect(
        key1 = listState.firstVisibleItemIndex,
        key2 = isVisible.value,
        block = {
            callback()
        })
}