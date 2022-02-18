package com.xiangxue.news.homefragment.headlinenews

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.xiangxue.base.mvvm.view.StatusComposable
import com.xiangxue.news.R
import kotlinx.coroutines.launch
import com.xiangxue.news.homefragment.newslist.NewsListComposable
import com.xiangxue.news.homefragment.newslist.NewsListComposableWithSmartRefreshLayout
import com.xiangxue.news.homefragment.newslist.NewsListComposableWithSwipeToRefresh

@ExperimentalPagerApi
@Composable
fun HeadlineNewsComposable() {
    val viewModel: HeadlineNewsViewModel = viewModel()
    var tabIndex by remember { mutableStateOf(0) }
    val pagerState = rememberPagerState(initialPage = 0)
    val coroutineScope = rememberCoroutineScope()
    val listState = remember {
        val list = mutableListOf(mutableStateOf(false))
        repeat(viewModel.dataList.size) {
            list.add(it, mutableStateOf(false))
        }
        list
    }
    Column {
        ScrollableTabRow(
            backgroundColor = colorResource(R.color.colorAccent),
            contentColor = Color.White,
            selectedTabIndex = pagerState.currentPage,
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            edgePadding = 0.dp,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            }) {
            viewModel.dataList.forEachIndexed { index, text ->
                val selected = tabIndex == index
                val interactionSource = remember {
                    MutableInteractionSource()
                }
                val isPress = interactionSource.collectIsPressedAsState().value
                Tab(selected = selected,
                    text = {
                        Text(
                            text = text.name,
                            color = if (isPress || pagerState.currentPage == index) Color.White else Color.Gray
                        )
                    },
                    onClick = {
                        tabIndex = index
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    })
            }
        }
        HorizontalPager(
            count = viewModel.dataList.size,
            state = pagerState
        ) { index ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                key(viewModel.dataList[index].channelId) {
                    StatusComposable(mvvmBaseViewModel = viewModel) {
                        NewsListComposableWithSwipeToRefresh(listState[index], viewModel.dataList[index])
                    }
                }
            }
        }
    }
    LaunchedEffect(
        key1 = pagerState.currentPage,
        block = {
            listState.forEach {
                it.value = false
            }
            listState[pagerState.currentPage].value = true
        })
}