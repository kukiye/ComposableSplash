package com.xiangxue.base.mvvm.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.xiangxue.base.mvvm.model.MvvmBaseModel
import com.xiangxue.base.mvvm.model.IBaseModelListener
import com.xiangxue.base.mvvm.model.PagingResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
abstract class MvvmBaseViewModel<M : MvvmBaseModel<*, *>?, D>(val savedStateHandle: SavedStateHandle) :
    ViewModel(), LifecycleObserver,
    IBaseModelListener<List<D>?> {
    protected var model: M? = null
    var dataList = mutableStateListOf<D>()
    var viewStatus = mutableStateOf(ViewStatus.LOADING)
    var errorMessage: String = ""
    private val _isRefreshing = MutableStateFlow(false)

    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    fun tryToRefresh() {
        if (model == null) {
            model = createModel()
        }
        if (model != null) {
            viewModelScope.launch {
                _isRefreshing.emit(true)
                model!!.refresh()
            }
        }
    }

    fun tryToLoadNextPage() {
        if (model == null) {
            model = createModel()
        }
        model!!.loadNextPage()
    }

    override fun onLoadSuccess(
        model: MvvmBaseModel<*, *>?,
        data: List<D>?,
        vararg pagingResult: PagingResult?
    ) {
        if (model === this.model) {
            if (model != null) {
                if (model.isPaging) {
                    if (pagingResult[0]!!.isFirstPage) {
                        dataList.clear()
                        viewModelScope.launch {
                            _isRefreshing.emit(false)
                        }
                    }
                    if (pagingResult[0]!!.isEmpty) {
                        if (pagingResult[0]!!.isFirstPage) {
                            viewStatus.value = ViewStatus.EMPTY
                        } else {
                            viewStatus.value = ViewStatus.NO_MORE_DATA
                        }
                    } else {
                        if (data != null) {
                            dataList.addAll(data)
                        }
                        viewStatus.value = ViewStatus.SHOW_CONTENT
                    }
                } else {
                    dataList.clear()
                    if (data != null) {
                        dataList.addAll(data)
                    }
                    viewStatus.value = ViewStatus.SHOW_CONTENT
                }
            }
        }
    }

    override fun onLoadFail(
        model: MvvmBaseModel<*, *>,
        prompt: String,
        vararg pagingResult: PagingResult
    ) {
        errorMessage = prompt
        if (model.isPaging && !pagingResult[0].isFirstPage) {
            viewStatus.value = ViewStatus.LOAD_MORE_FAILED
        } else if (dataList.size == 0) {
            viewStatus.value = ViewStatus.REFRESH_ERROR
        } else {
            viewStatus.value = ViewStatus.REFRESH_ERROR_AND_CACHE_LOADED
        }
        if(model.isPaging && pagingResult[0].isFirstPage) {
            viewModelScope.launch {
                _isRefreshing.emit(false)
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onResume() {
        if (dataList.size == 0) {
            model!!.refresh()
        }
    }

    protected abstract fun createModel(): M

    init {
        tryToRefresh()
    }
}