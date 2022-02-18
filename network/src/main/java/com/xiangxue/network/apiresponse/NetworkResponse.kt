package com.xiangxue.network.apiresponse

sealed class NetworkResponse<out T : Any> {
    data class Success<T : Any>(val body: T) : NetworkResponse<T>()
    data class ApiError(val body: Any, val code: Int) : NetworkResponse<Nothing>()
    data class NetworkError(val message:Any, val code: Int) : NetworkResponse<Nothing>()
    data class UnknownError(val error: Throwable?) : NetworkResponse<Nothing>()
}
