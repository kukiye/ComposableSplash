package com.xiangxue.base.compose.viewmodel

import androidx.compose.runtime.mutableStateOf

open class IBaseViewModel {
    @Transient
    open var isResumed = mutableStateOf(false)
}

