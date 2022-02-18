package com.xiangxue.news.homefragment.api

import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class NewsListBean(
    @Json(name = "pagebean")
    val pagebean: Pagebean?,
    @Json(name = "ret_code")
    val retCode: Int?
)

@JsonClass(generateAdapter = true)
data class Pagebean(
    @Json(name = "allNum")
    val allNum: Int?,
    @Json(name = "allPages")
    val allPages: Int?,
    @Json(name = "contentlist")
    val contentlist: List<Contentlist>?,
    @Json(name = "currentPage")
    val currentPage: Int?,
    @Json(name = "maxResult")
    val maxResult: Int?
)

@JsonClass(generateAdapter = true)
data class Contentlist(
    @Json(name = "channelId")
    val channelId: String?,
    @Json(name = "channelName")
    val channelName: String?,
    @Json(name = "imageurls")
    val imageurls: List<Imageurl>?,
    @Json(name = "link")
    val link: String?,
    @Json(name = "pubDate")
    val pubDate: String?,
    @Json(name = "source")
    val source: String?,
    @Json(name = "title")
    val title: String?
)

@JsonClass(generateAdapter = true)
data class Imageurl(
    @Json(name = "height")
    val height: String?,
    @Json(name = "url")
    val url: String?,
    @Json(name = "width")
    val width: String?
)