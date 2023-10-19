/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.trail

class UseCase private constructor(val value: String) {
    constructor(useCase: UseCaseCommon) : this(useCase.value)

    companion object {
        fun custom(useCase: String): UseCase {
            return UseCase("custom:$useCase")
        }

        fun from(useCase: String): UseCase {
            val common: UseCaseCommon? = UseCaseCommon.from(useCase)
            return if (common != null) UseCase(common)
            else if (useCase.startsWith("custom:")) UseCase(useCase)
            else custom(useCase)
        }
    }
}
