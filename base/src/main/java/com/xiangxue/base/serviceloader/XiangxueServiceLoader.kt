package com.xiangxue.base.serviceloader

import java.lang.Exception
import java.util.*

object XiangxueServiceLoader {
    fun <S> load(service: Class<S>): S? {
        return try {
            ServiceLoader.load(service).iterator().next()
        } catch (e: Exception) {
            null
        }
    }
}