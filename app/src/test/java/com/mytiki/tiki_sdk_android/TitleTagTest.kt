/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android

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
        val json = "\"advertising_data\""
        val titleTag = TitleTag.fromJson(json)
        Assert.assertEquals(TitleTag.ADVERTISING_DATA.value, titleTag.value)
    }

    @Test
    fun titleTag_to_json() {
        val titleTag = TitleTag.ADVERTISING_DATA
        val json = titleTag.toJson()
        Assert.assertEquals("\"advertising_data\"", json)
    }


}