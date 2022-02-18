package com.xiangxue.network.apiresponse

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class NetworkResponseAdapter<S : Any>(
    private val delegate: Type,
    private val errorBodyConverter: NetworkResponseAdapterFactory.FailureHandler?
) : CallAdapter<S, Call<NetworkResponse<S>>> {

    override fun responseType(): Type = delegate

    override fun adapt(call: Call<S>): Call<NetworkResponse<S>> {
        return NetworkResponseCall(call, errorBodyConverter)
    }
}
