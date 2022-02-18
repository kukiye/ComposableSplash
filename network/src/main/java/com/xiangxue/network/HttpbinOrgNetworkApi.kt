package com.xiangxue.network

import com.xiangxue.network.base.BaseNetworkApi
import okhttp3.Interceptor

object HttpbinOrgNetworkApi : BaseNetworkApi() {

    override fun getInterceptor(): Interceptor? {
        return null
    }

    override fun getFormal(): String {
        return "https://httpbin.org/"
    }

    override fun getTest(): String {
        return "https://httpbin.org/"
    }
}