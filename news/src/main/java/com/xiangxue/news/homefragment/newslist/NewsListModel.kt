package com.xiangxue.news.homefragment.newslist

import com.xiangxue.news.homefragment.api.NewsListBean
import com.xiangxue.base.compose.viewmodel.IBaseViewModel
import com.xiangxue.base.mvvm.model.MvvmBaseModel
import com.xiangxue.base.mvvm.model.IBaseModelListener
import com.xiangxue.network.apiresponse.NetworkResponse
import com.xiangxue.network.TecentNetworkWithoutEnvelopeApi
import com.xiangxue.news.homefragment.api.NewsApiInterface
import com.xiangxue.news.homefragment.newslist.composables.title.TitleViewModel
import com.xiangxue.news.homefragment.newslist.composables.titlepicture.TitlePictureViewModel
import com.xiangxue.news.homefragment.newslist.utils.NewsListCache
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.ArrayList

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */

class NewsListModel(
    private val channelId: String,
    private val channelName: String,
    iBaseModelListener: IBaseModelListener<List<IBaseViewModel>>,
    private val viewModelScope: CoroutineScope
) :
    MvvmBaseModel<NewsListBean?, List<IBaseViewModel>>(
        true,
        iBaseModelListener,
        "pref_key_news_$channelId",
        NewsListCache.getNewsListCache(channelId),
        1
    ) {
    override fun load() {
/*        viewModelScope.launch {
            val newsListBean = TecentNetworkWithoutEnvelopeApi.getService(
                NewsApiInterface::class.java
            ).getNewsList(channelId, channelName, pageNumber.toString())
            when (newsListBean) {
                is NetworkResponse.Success -> {
                    onSuccess(newsListBean.body, false)
                }
                is NetworkResponse.ApiError -> {
                    onFailure(newsListBean.body.toString())
                }
                is NetworkResponse.NetworkError -> {
                    onFailure(newsListBean.message.toString())
                }
                is NetworkResponse.UnknownError -> {
                    onFailure(newsListBean.error?.message)
                }
            }
        }*/
    }

    override fun onSuccess(t: NewsListBean?, isFromCache: Boolean) {
        val baseViewModels: ArrayList<IBaseViewModel> = ArrayList<IBaseViewModel>()
        for (source in (t)!!.pagebean!!.contentlist!!) {
            if (source.imageurls != null && source.imageurls.size > 1) {
                val viewModel =
                    TitlePictureViewModel(source.title ?: "", source.imageurls[0].url ?: "")
                baseViewModels.add(viewModel)
            } else {
                val viewModel = TitleViewModel(source.title ?: "")
                baseViewModels.add(viewModel)
            }
        }
        notifyResultsToListener(t, baseViewModels, isFromCache)
    }

    override fun onFailure(e: String?) {
        notifyFailureToListener(e)
    }
}