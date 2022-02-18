package com.xiangxue.base.utils

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.Type

object MoshiUtils {
    val moshiBuild: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory())
        .build()

    fun <T> fromJson(json: String, type: Type): T? {
        if(json == null || json == "") {
            return null
        }
        val adapter = moshiBuild.adapter<T>(type).lenient()
        return adapter.fromJson(json)
    }

    inline fun <reified T> fromJson(json: String): T? {
        val adapter = moshiBuild.adapter(T::class.java).lenient()
        return adapter.fromJson(json)
    }

    inline fun <reified T> toJson(t: T) = moshiBuild.adapter(T::class.java).toJson(t) ?: ""

    inline fun <reified T> listFromJson(json: String): MutableList<T> = fromJson<MutableList<T>>(
        json, Types.newParameterizedType(
            MutableList::class.java, T::class.java
        )
    ) ?: mutableListOf()

    inline fun <reified K, reified V> mapFromJson(json: String): MutableMap<K, V> = fromJson(
        json,
        Types.newParameterizedType(MutableMap::class.java, K::class.java, V::class.java)
    ) ?: mutableMapOf()
}