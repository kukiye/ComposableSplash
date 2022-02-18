package com.xiangxue.base.mvvm.model

import com.squareup.moshi.Json
import com.xiangxue.base.preference.BasicDataPreferenceUtil
import com.xiangxue.base.utils.MoshiUtils

class CacheData {
    @Json(name  ="networkData")
    var networkData: Any? = null

    @JvmField
    @Json(name = "updateTimeInMillis")
    var updateTimeInMillis: Long = 0

    @Json(name = "resultData")
    var resultData: Any? = null
    val networkDataString: String
        get() = if (networkData != null) {
            MoshiUtils.toJson(networkData)
        } else ""
    val resultDataString: String
        get() = if (resultData != null) {
            MoshiUtils.toJson(resultData)
        } else ""

    companion object {
        @JvmStatic
        fun saveDataToPreference(cachedPreferenceKey: String?, data: Any?, resultData: Any?) {
            if (data != null) {
                val cachedData = CacheData()
                cachedData.networkData = data
                cachedData.resultData = resultData
                cachedData.updateTimeInMillis = System.currentTimeMillis()
                BasicDataPreferenceUtil.getInstance()
                    .setString(cachedPreferenceKey, MoshiUtils.toJson(cachedData))
            }
        }

        @JvmStatic
        fun isSameAsCached(cachedPreferenceKey: String?, resultData: Any?): Boolean {
            if (cachedPreferenceKey != null && resultData != null) {
                val cachedData: CacheData? = MoshiUtils.fromJson(
                    BasicDataPreferenceUtil.getInstance().getString(cachedPreferenceKey),
                    CacheData::class.java
                )
                if (cachedData != null) {
                    if (resultData != null && cachedData.resultDataString == MoshiUtils.toJson(
                            resultData
                        )
                    ) {
                        return true
                    }
                }
            }
            return false
        }
    }
}