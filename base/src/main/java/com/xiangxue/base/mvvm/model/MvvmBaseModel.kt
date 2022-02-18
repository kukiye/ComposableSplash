package com.xiangxue.base.mvvm.model

import com.xiangxue.base.preference.BasicDataPreferenceUtil
import com.xiangxue.base.utils.GenericUtils
import com.xiangxue.base.utils.MoshiUtils

abstract class MvvmBaseModel<NETWORK_DATA, RESULT_DATA>(
    val isPaging: Boolean,
    private val iBaseModelListener: IBaseModelListener<RESULT_DATA>,
    private val cachedPreferenceKey: String?,
    private val apkPredefinedData: String?,
    vararg initPageNumber: Int
) : MvvmDataObserver<NETWORK_DATA> {
    protected var pageNumber = 1
    private var INIT_PAGE_NUMBER = 0
    private var mIsLoading = false
    private var mIsFirstRefresh = true;
    protected fun isNeedToUpdate(cachedTimeSlot: Long): Boolean {
        return true
    }

    fun refresh() {
        // Need to throw exception if register is not called;
        if (!mIsLoading) {
            mIsLoading = true
            if (isPaging) {
                pageNumber = INIT_PAGE_NUMBER
            }
            if (mIsFirstRefresh) {
                mIsFirstRefresh = false
                readCachedData()
            }
            load()
        }
    }

    fun loadNextPage() {
        // Need to throw exception if register is not called;
        if (!mIsLoading) {
            mIsLoading = true
            load()
        }
    }

    private val isRefresh: Boolean
        get() = pageNumber == INIT_PAGE_NUMBER

    protected abstract fun load()
    protected fun notifyResultsToListener(
        networkData: NETWORK_DATA,
        resultData: RESULT_DATA?,
        isFromCache: Boolean
    ) {
        // notify
        if (isPaging) {
            iBaseModelListener.onLoadSuccess(
                this,
                resultData,
                PagingResult(
                    isRefresh,
                    if (resultData == null) true else (resultData as List<*>).isEmpty(),
                    (resultData as List<*>?)!!.isNotEmpty()
                )
            )
        } else if (isFromCache || !CacheData.isSameAsCached(cachedPreferenceKey, resultData)) {
            iBaseModelListener.onLoadSuccess(this, resultData)
        }

        // save resultData to preference
        if (!isFromCache) {
            if (isPaging) {
                if (cachedPreferenceKey != null && pageNumber == INIT_PAGE_NUMBER) {
                    CacheData.saveDataToPreference(cachedPreferenceKey, networkData, resultData)
                }
                // update page number
                if (resultData != null && (resultData as List<*>).size > 0) {
                    pageNumber++
                }
            } else if (cachedPreferenceKey != null){
                CacheData.saveDataToPreference(cachedPreferenceKey, networkData, resultData)
            }
        }
        mIsLoading = false
    }

    protected fun notifyFailureToListener(errorMessage: String?) {
        if (isPaging) {
            iBaseModelListener.onLoadFail(
                this,
                errorMessage,
                PagingResult(isRefresh, true, false)
            )
        } else {
            iBaseModelListener.onLoadFail(this, errorMessage)
        }

        mIsLoading = false
    }

    private fun readCachedData() {
        if (cachedPreferenceKey != null) {
            val cachedData: CacheData? = MoshiUtils.fromJson(
                BasicDataPreferenceUtil.getInstance().getString(cachedPreferenceKey),
                CacheData::class.java
            )
            if (cachedData != null) {
                val savedData: NETWORK_DATA? = MoshiUtils.fromJson(
                    cachedData.networkDataString,
                    GenericUtils.getGenericType(BaseMvvmModel@ this)
                )
                if (savedData != null) {
                    onSuccess(savedData, true)
                }
                if (!isNeedToUpdate(cachedData.updateTimeInMillis)) {
                    return
                }
            } else if (apkPredefinedData != null) {
                val savedData: NETWORK_DATA? = MoshiUtils.fromJson(
                    apkPredefinedData,
                    GenericUtils.getGenericType(BaseMvvmModel@ this)
                )
                if (savedData != null) {
                    onSuccess(savedData, true)
                }
            }
        }
    }

    init {
        INIT_PAGE_NUMBER = if (initPageNumber != null && initPageNumber.size == 1) {
            initPageNumber[0]
        } else {
            -1
        }
    }
}