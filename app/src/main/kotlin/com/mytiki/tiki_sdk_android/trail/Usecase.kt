/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.trail

class Usecase private constructor(val value: String) {
    constructor(usecase: UsecaseCommon) : this(usecase.value)

    companion object {
        fun custom(usecase: String): Usecase {
            return Usecase("custom:$usecase")
        }

        fun from(usecase: String): Usecase {
            val common: UsecaseCommon? = UsecaseCommon.from(usecase)
            return if (common != null) Usecase(common) else custom(usecase)
        }
    }
}