package com.xiangxue.news.homefragment.headlinenews

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.xiangxue.base.mvvm.model.IBaseModelListener
import com.xiangxue.base.mvvm.viewmodel.MvvmBaseViewModel
import com.xiangxue.news.homefragment.api.Channel

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
class HeadlineNewsViewModel(savedStateHandle: SavedStateHandle) :
    MvvmBaseViewModel<ChannelsModel, Channel>(savedStateHandle) {
    override fun createModel(): ChannelsModel {
        return ChannelsModel(
            HeadlineNewsViewModel@ this as IBaseModelListener<List<Channel>>,
            viewModelScope
        )
    }
}