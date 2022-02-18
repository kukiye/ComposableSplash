package com.xiangxue.network

import com.xiangxue.network.base.BaseNetworkApi
import com.xiangxue.network.utils.TecentUtil
import okhttp3.Interceptor

object TecentNetworkWithEnvelopeApi : BaseNetworkApi() {

    override fun getInterceptor(): Interceptor? {
        return Interceptor { chain ->
            val timeStr = TecentUtil.getTimeStr()
            val builder = chain.request().newBuilder()
            builder.addHeader("Source", "source")
            builder.addHeader("Authorization", TecentUtil.getAuthorization(timeStr))
            builder.addHeader("Date", timeStr)
            chain.proceed(builder.build())
        }
    }

    override fun getFormal(): String {
        return "http://service-o5ikp40z-1255468759.ap-shanghai.apigateway.myqcloud.com/"
    }

    override fun getTest(): String {
        return "http://service-o5ikp40z-1255468759.ap-shanghai.apigateway.myqcloud.com/"
    }
}