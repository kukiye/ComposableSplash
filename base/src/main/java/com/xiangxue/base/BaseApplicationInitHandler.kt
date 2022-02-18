package com.xiangxue.base

import com.google.auto.service.AutoService
import com.xiangxue.base.serviceloader.application.IApplicationInitHandler
import com.xiangxue.base.preference.PreferencesUtil

@AutoService(IApplicationInitHandler::class)
class BaseApplicationInitHandler : IApplicationInitHandler {
    override fun onApplicationInitWhetherPrivacyAgreedOrNot(
        application: BaseApplication,
        isMainProcess: Boolean
    ) {
        if (isMainProcess) {
            PreferencesUtil.init(application)
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