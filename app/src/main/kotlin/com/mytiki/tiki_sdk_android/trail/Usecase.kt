/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.trail

class Usecase private constructor(val value: String) {
    constructor(useCase: UsecaseCommon) : this(useCase.value)

    companion object {
        fun custom(useCase: String): Usecase {
            return Usecase("custom:$useCase")
        }

        fun from(useCase: String): Usecase {
            val common: UsecaseCommon? = UsecaseCommon.from(useCase)
            return if (common != null) Usecase(common)
            else if (useCase.startsWith("custom:")) Usecase(useCase)
            else custom(useCase)
        }
    }
}
