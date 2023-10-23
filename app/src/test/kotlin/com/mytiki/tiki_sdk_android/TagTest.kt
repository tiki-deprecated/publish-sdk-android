/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android

import com.mytiki.tiki_sdk_android.trail.Tag
import com.mytiki.tiki_sdk_android.trail.TagCommon
import org.junit.Test

class TagTest {
    @Test
    fun tagEnum_from() {
        val existingTagEnum = TagCommon.from("advertising_data")
        val nullTagEnum = TagCommon.from("null")

        assert(existingTagEnum == TagCommon.ADVERTISING_DATA)
        assert(nullTagEnum == null)
    }

    @Test
    fun tag_from() {
        val test = "test"

        val testCase1 = Tag.from("advertising_data")
        val testCase2 = Tag.from("custom:$test")
        val testCase3 = Tag.from(test)

        assert(testCase1.value == TagCommon.ADVERTISING_DATA.value)
        assert(testCase2.value == "custom:$test")
        assert(testCase3.value == "custom:$test")
    }

    @Test
    fun tag_custom() {
        val test = "test"
        val tag = Tag.custom(test)
        assert(tag.value == "custom:$test")
    }

    @Test
    fun tag_constructor() {
        val tag = Tag(TagCommon.ADVERTISING_DATA)
        assert(tag.value == TagCommon.ADVERTISING_DATA.value)
    }


}
