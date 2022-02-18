package com.xiangxue.news.homefragment

import androidx.compose.runtime.Composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.auto.service.AutoService
import com.xiangxue.common.components.NewsService
import com.xiangxue.news.homefragment.headlinenews.HeadlineNewsComposable

@AutoService(NewsService::class)
class NewsServiceImpl : NewsService {

    @ExperimentalPagerApi
    override fun getNewsScreen(): @Composable () -> Unit {
        return { HeadlineNewsComposable() }
    }
}