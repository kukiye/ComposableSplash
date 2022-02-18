package com.xiangxue.news.homefragment.newslist.composables.titlepicture

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.xiangxue.base.compose.viewmodel.IBaseViewModel

data class TitlePictureViewModel(
    var title: String,
    var picture: String,
    @Transient
    var isFirstVisible: MutableState<Boolean> = mutableStateOf(false)
) : IBaseViewModel()
