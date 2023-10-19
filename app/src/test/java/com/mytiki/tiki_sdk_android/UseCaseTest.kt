/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android

import com.mytiki.tiki_sdk_android.trail.Tag
import com.mytiki.tiki_sdk_android.trail.TagCommon
import com.mytiki.tiki_sdk_android.trail.UseCase
import com.mytiki.tiki_sdk_android.trail.UseCaseCommon
import org.junit.Test

class UseCaseTest {
    @Test
    fun useCaseCommon_from() {
        val existingUseCaseCommon = UseCaseCommon.from("support")
        val nullUseCaseCommon = UseCaseCommon.from("null")

        assert(existingUseCaseCommon == UseCaseCommon.SUPPORT)
        assert(nullUseCaseCommon == null)
    }
    @Test
    fun useCase_from() {
        val test = "test"

        val testCase1 = UseCase.from("support")
        val testCase2 =  UseCase.from("custom:$test")
        val testCase3 =  UseCase.from(test)

        assert(testCase1.value == UseCaseCommon.SUPPORT.value)
        assert(testCase2.value == "custom:$test")
        assert(testCase3.value == "custom:$test")
    }
    @Test
    fun useCase_custom() {
        val test = "test"
        val useCase = UseCase.custom(test)
        assert(useCase.value == "custom:$test")
    }
    @Test
    fun useCase_constructor() {
        val useCase = UseCase(UseCaseCommon.SUPPORT)
        assert(useCase.value == UseCaseCommon.SUPPORT.value)
    }
}
