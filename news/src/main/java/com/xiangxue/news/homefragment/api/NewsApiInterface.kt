package com.xiangxue.news.homefragment.api

import com.xiangxue.network.apiresponse.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
interface NewsApiInterface {
    @GET("release/news")
    suspend fun getNewsList(
        @Query("channelId") channelId: String?,
        @Query("channelName") channelName: String?,
        @Query("page") page: String?
    ): NetworkResponse<NewsListBean>?


    @GET("release/channel")
    suspend fun getNewsChannelsWithoutEnvelope(
    ): NetworkResponse<NewsChannelsBean>

}