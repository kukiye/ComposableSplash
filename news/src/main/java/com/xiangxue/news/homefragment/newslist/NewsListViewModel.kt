package com.xiangxue.news.homefragment.newslist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.xiangxue.base.compose.viewmodel.IBaseViewModel
import com.xiangxue.base.mvvm.model.IBaseModelListener
import com.xiangxue.base.mvvm.viewmodel.MvvmBaseViewModel

class NewsListViewModel(savedStateHandle: SavedStateHandle) :
    MvvmBaseViewModel<NewsListModel, IBaseViewModel>(savedStateHandle) {
    override fun createModel(): NewsListModel {
        return NewsListModel(
            savedStateHandle.get(BUNDLE_KEY_PARAM_CHANNEL_ID)!!,
            savedStateHandle.get(BUNDLE_KEY_PARAM_CHANNEL_NAME)!!,
            NewsListViewModel@ this as IBaseModelListener<List<IBaseViewModel>>,
            viewModelScope
        )
    }
}