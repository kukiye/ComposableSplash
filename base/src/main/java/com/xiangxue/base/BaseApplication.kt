package com.xiangxue.base

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Process
import com.xiangxue.base.preference.PreferencesUtil
import com.xiangxue.base.serviceloader.application.IApplicationInitHandler
import java.util.*

const val PRIVACY_POLICY_PREF_KEY = "privacy_policy_pref_key"
open abstract class BaseApplication : Application() {
    private var sApplicationInitHandlers: MutableList<IApplicationInitHandler> =
        ArrayList<IApplicationInitHandler>()

    companion object {
        var INSTANCE: BaseApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        handleApplicationInitWhetherPrivacyAgreedOrNot()
        if (PreferencesUtil.getInstance().getAppStatusBoolean(PRIVACY_POLICY_PREF_KEY, false)) {
            handleApplicationInitWithPrivacyAgreed()
        }
    }

    init {
        val serviceLoader = ServiceLoader.load(
            IApplicationInitHandler::class.java
        )
        for (handler in serviceLoader) {
            sApplicationInitHandlers.add(handler!!)
        }
    }

    private fun handleApplicationInitWhetherPrivacyAgreedOrNot() {
        for (iApplicationInitHandler in sApplicationInitHandlers) {
            if (iApplicationInitHandler != null) {
                iApplicationInitHandler.onApplicationInitWhetherPrivacyAgreedOrNot(
                    this,
                    isMainProcess()
                )
            }
        }
    }

    private fun handleApplicationInitWithPrivacyAgreed() {
        for (iApplicationInitHandler in sApplicationInitHandlers) {
            if (iApplicationInitHandler != null) {
                iApplicationInitHandler.onApplicationInitWithPrivacyAgreed(this, isMainProcess())
            }
        }
    }

    private fun handleApplicationInitWithPrivacyDenied(application: BaseApplication?) {
        for (iApplicationInitHandler in sApplicationInitHandlers) {
            if (iApplicationInitHandler != null) {
                iApplicationInitHandler.onApplicationInitWithPrivacyDenied(this, isMainProcess())
            }
        }
    }

    protected open fun isMainProcess(): Boolean {
        val processName = getProcessName(this)
        if (processName != null) { // 按进程名初始化
            if (processName == getApplicationId()) {
                return true
            }
        }
        return false
    }

    /**
     * 获取进程名
     *
     * @param context
     * @return
     */
    open fun getProcessName(context: Context): String? {
        val am = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val runningApps = am.runningAppProcesses ?: return null
        for (proInfo in runningApps) {
            if (proInfo.pid == Process.myPid()) {
                if (proInfo.processName != null) {
                    return proInfo.processName
                }
            }
        }
        return null
    }

    abstract fun getApplicationId(): String

    abstract fun getApplicationVersion(): String

    abstract fun getApplicationCode(): String
}