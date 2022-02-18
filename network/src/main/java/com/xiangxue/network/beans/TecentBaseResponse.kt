package com.xiangxue.network.beans

import com.squareup.moshi.Json

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
open class TecentBaseResponse {
    @Json(name = "showapi_res_code")
    val showapiResCode: Int? = null

    @Json(name = "showapi_res_error")
    val showapiResError: String? = null
}