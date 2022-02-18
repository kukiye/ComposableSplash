package com.xiangxue.composemvvm.application

import com.xiangxue.base.BaseApplication
import com.xiangxue.composemvvm.BuildConfig

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
class ComposeMvvmApplication : BaseApplication() {

    override fun getApplicationId(): String {
        return BuildConfig.APPLICATION_ID
    }

    override fun getApplicationVersion(): String {
        return BuildConfig.VERSION_NAME
    }

    override fun getApplicationCode(): String {
        return BuildConfig.VERSION_CODE.toString()
    }
}