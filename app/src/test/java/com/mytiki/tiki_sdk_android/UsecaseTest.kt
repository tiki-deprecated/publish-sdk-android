/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android

import com.mytiki.tiki_sdk_android.trail.Usecase
import com.mytiki.tiki_sdk_android.trail.UsecaseCommon
import org.junit.Test

class UsecaseTest {
    @Test
    fun useCaseCommon_from() {
        val existingUsecaseCommon = UsecaseCommon.from("support")
        val nullUsecaseCommon = UsecaseCommon.from("null")

        assert(existingUsecaseCommon == UsecaseCommon.SUPPORT)
        assert(nullUsecaseCommon == null)
    }
    @Test
    fun useCase_from() {
        val test = "test"

        val testCase1 = Usecase.from("support")
        val testCase2 =  Usecase.from("custom:$test")
        val testCase3 =  Usecase.from(test)

        assert(testCase1.value == UsecaseCommon.SUPPORT.value)
        assert(testCase2.value == "custom:$test")
        assert(testCase3.value == "custom:$test")
    }
    @Test
    fun useCase_custom() {
        val test = "test"
        val usecase = Usecase.custom(test)
        assert(usecase.value == "custom:$test")
    }
    @Test
    fun useCase_constructor() {
        val usecase = Usecase(UsecaseCommon.SUPPORT)
        assert(usecase.value == UsecaseCommon.SUPPORT.value)
    }
}
