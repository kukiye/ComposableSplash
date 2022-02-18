package com.xiangxue.common.components

import androidx.compose.runtime.Composable

interface NewsService {
    fun getNewsScreen(): @Composable () -> Unit
}