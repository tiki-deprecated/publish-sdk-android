/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.junit.Assert
import org.junit.Test

class TikiSdkDestinationTest {
    @Test
    fun encode_TikiSdkDestination_from_JSON() {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<TikiSdkDestination> = moshi.adapter(TikiSdkDestination::class.java)
        val tikiSdkDestination = adapter.fromJson("{\"uses\":[\"*\"], \"paths\": [\"*\"]}")
        Assert.assertEquals("*", tikiSdkDestination?.uses?.get(0))
        Assert.assertEquals("*", tikiSdkDestination?.paths?.get(0))
    }

    @Test
    fun encode_TikiSdkDestination_ALL_and_NONE_to_JSON() {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<TikiSdkDestination> = moshi.adapter(TikiSdkDestination::class.java)
        val tikiSdkDestinationAll = TikiSdkDestination.ALL
        val allJson = adapter.toJson(tikiSdkDestinationAll)
        Assert.assertEquals("{\"paths\":[\"*\"],\"uses\":[\"*\"]}", allJson)
        val tikiSdkDestinationNone = TikiSdkDestination.NONE
        val jsonNone = adapter.toJson(tikiSdkDestinationNone)
        Assert.assertEquals("{\"paths\":[],\"uses\":[]}", jsonNone)
    }
}