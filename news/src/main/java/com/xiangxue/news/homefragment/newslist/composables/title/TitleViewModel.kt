package com.xiangxue.news.homefragment.newslist.composables.title

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.xiangxue.base.compose.viewmodel.IBaseViewModel

data class TitleViewModel(
    var title: String,
    @Transient
    var isFirstVisible: MutableState<Boolean> = mutableStateOf(false)
) : IBaseViewModel()
