package com.xiangxue.base.serviceloader.application;

import com.xiangxue.base.BaseApplication;

public interface IApplicationInitHandler {
    // 无论用户隐私协议是否同意都可以执行的代码
    void onApplicationInitWhetherPrivacyAgreedOrNot(BaseApplication application, boolean isMainProcess);

    // 只有用户隐私协议同意了以后才可以执行的代码
    void onApplicationInitWithPrivacyAgreed(BaseApplication application, boolean isMainProcess);

    // 只有用户隐私协议拒绝后执行的代码
    void onApplicationInitWithPrivacyDenied(BaseApplication application, boolean isMainProcess);
}
