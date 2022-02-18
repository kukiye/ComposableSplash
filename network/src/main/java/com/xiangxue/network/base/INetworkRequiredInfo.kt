package com.xiangxue.network.base

import android.app.Application

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
interface INetworkRequiredInfo {
    val appVersionName: String?
    val appVersionCode: String?
    val isDebug: Boolean
    val applicationContext: Application?
}