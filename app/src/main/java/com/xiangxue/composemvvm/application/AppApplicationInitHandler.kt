package com.xiangxue.composemvvm.application

import com.google.auto.service.AutoService
import com.xiangxue.base.serviceloader.application.IApplicationInitHandler
import com.xiangxue.base.BaseApplication
import com.xiangxue.network.base.BaseNetworkApi

@AutoService(IApplicationInitHandler::class)
class AppApplicationInitHandler : IApplicationInitHandler {
    override fun onApplicationInitWhetherPrivacyAgreedOrNot(
        application: BaseApplication,
        isMainProcess: Boolean
    ) {
        if (isMainProcess) {
            BaseNetworkApi.init(NetworkRequestInfo(application))
        }
    }

    override fun onApplicationInitWithPrivacyAgreed(
        application: BaseApplication,
        isMainProcess: Boolean
    ) {
    }

    override fun onApplicationInitWithPrivacyDenied(
        application: BaseApplication,
        isMainProcess: Boolean
    ) {
    }
}