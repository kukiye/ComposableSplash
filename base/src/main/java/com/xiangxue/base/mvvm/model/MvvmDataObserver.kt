package com.xiangxue.base.mvvm.model

interface MvvmDataObserver<DATA> {
    fun onSuccess(t: DATA, isFromCache: Boolean)
    fun onFailure(message: String?)
}