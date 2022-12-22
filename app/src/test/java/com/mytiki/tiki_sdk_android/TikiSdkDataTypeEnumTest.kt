package com.mytiki.tiki_sdk_android

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.junit.Assert
import org.junit.Test

class TikiSdkDataTypeEnumTest {
    @Test
    fun encode_TikiSdkDataTypeEnum_from_JSON() {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<TikiSdkDataTypeEnum> =
            moshi.adapter(TikiSdkDataTypeEnum::class.java)
        val tikiSdkDataTypeEnum = adapter.fromJson("\"data_pool\"")
        Assert.assertEquals(TikiSdkDataTypeEnum.data_pool.name, tikiSdkDataTypeEnum?.name)
    }

    @Test
    fun encode_TikiSdkDataTypeEnum_ALL_and_NONE_to_JSON() {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<TikiSdkDataTypeEnum> =
            moshi.adapter(TikiSdkDataTypeEnum::class.java)
        val tikiSdkDataTypeEnum = TikiSdkDataTypeEnum.data_pool
        val poolJson = adapter.toJson(tikiSdkDataTypeEnum)
        Assert.assertEquals("\"data_pool\"", poolJson)
    }
}