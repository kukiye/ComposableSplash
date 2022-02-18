package com.xiangxue.network.apiresponse

import com.squareup.moshi.*
import com.xiangxue.network.error.BusinessException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class MoshiResultTypeAdapterFactory(
    private val envelope: Envelope?
) : JsonAdapter.Factory {
    interface Envelope {
        fun getStatusCodeKey(): String
        fun getErrorMessageKey(): String
        fun getDataKey(): String
        fun doesStatusCodeIndicateSuccess(statusCode: Int): Boolean
    }

    override fun create(
        type: Type,
        annotations: MutableSet<out Annotation>,
        moshi: Moshi
    ): JsonAdapter<*>? {
        val rawType = type.rawType
        if (rawType != NetworkResponse::class.java) return null
        val dataType: Type =
            (type as? ParameterizedType)?.actualTypeArguments?.firstOrNull() ?: return null
        val dataTypeAdapter = moshi.nextAdapter<Any>(this, dataType, annotations)
        return ResultTypeAdapter(dataTypeAdapter, envelope)
    }

    class ResultTypeAdapter<T>(
        private val dataTypeAdapter: JsonAdapter<T>,
        private val envelope: Envelope?
    ) : JsonAdapter<T>() {
        override fun fromJson(reader: JsonReader): T? {
            if (envelope != null) {
                reader.beginObject()
                var errcode: Int? = null
                var msg: String? = null
                var data: Any? = null

                while (reader.hasNext()) {
                    when (reader.nextName()) {
                        envelope.getStatusCodeKey() -> errcode = reader.nextString().toIntOrNull()
                        envelope.getErrorMessageKey() -> msg = reader.nextString()
                        envelope.getDataKey() -> data = dataTypeAdapter.fromJson(reader)
                        else -> reader.skipValue()
                    }
                }
                reader.endObject()
                if (errcode == null)
                    throw JsonDataException("Expected field [errcode] not present.")

                return if (envelope.doesStatusCodeIndicateSuccess(errcode)) data as T
                else throw BusinessException(errcode, msg)
            } else {
                return dataTypeAdapter.fromJson(reader) as T
            }
        }

        override fun toJson(writer: JsonWriter, value: T?): Unit = TODO("Not yet implemented")
    }
}
