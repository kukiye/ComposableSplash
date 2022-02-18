package com.xiangxue.composemvvm.application

import android.app.Application
import com.xiangxue.composemvvm.BuildConfig
import com.xiangxue.network.base.INetworkRequiredInfo

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
class NetworkRequestInfo(override val applicationContext: Application) : INetworkRequiredInfo {
    override val appVersionName: String
        get() = BuildConfig.VERSION_NAME
    override val appVersionCode: String
        get() = BuildConfig.VERSION_CODE.toString()
    override val isDebug: Boolean
        get() = BuildConfig.DEBUG
}