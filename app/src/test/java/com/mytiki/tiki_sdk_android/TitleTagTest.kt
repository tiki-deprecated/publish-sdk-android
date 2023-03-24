/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android
import com.mytiki.tiki_sdk_android.util.TitleTagFromStringAdapter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.junit.Assert
import org.junit.Test

class TitleTagTest {
    @Test
    fun titleTagEnum_from_value() {
        val titleTagEnum = TitleTagEnum.fromValue("advertising_data")
        assert(titleTagEnum == TitleTagEnum.ADVERTISING_DATA)
    }

    @Test
    fun titleTag_from_value() {
        val titleTag = TitleTag("advertising_data")
        assert(titleTag.value == TitleTagEnum.ADVERTISING_DATA.value)
    }

    @Test
    fun titleTag_from_json() {
        val moshi: Moshi = Moshi.Builder()
            .add(TitleTagFromStringAdapter())
            .build()
        val adapter: JsonAdapter<TitleTag> = moshi.adapter(TitleTag::class.java)
        val json = "\"advertising_data\""
        val titleTag = adapter.fromJson(json)
        Assert.assertEquals(TitleTag.ADVERTISING_DATA.value, titleTag?.value)
    }

    @Test
    fun titleTag_to_json() {
        val moshi: Moshi = Moshi.Builder()
            .add(TitleTagFromStringAdapter())
            .build()
        val adapter: JsonAdapter<TitleTag> = moshi.adapter(TitleTag::class.java)
        val titleTag = TitleTag.ADVERTISING_DATA
        val json = adapter.toJson(titleTag)
        Assert.assertEquals("\"advertising_data\"", json)
    }
}