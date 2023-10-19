/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.trail

enum class UseCaseCommon(val value: String) {
    ATTRIBUTION("attribution"),
    RETARGETING("retargeting"),
    PERSONALIZATION("personalization"),
    AI_TRAINING("ai_training"),
    DISTRIBUTION("distribution"),
    ANALYTICS("analytics"),
    SUPPORT("support");

    companion object {
        fun from(value: String): UseCaseCommon? {
            for (type in UseCaseCommon.values()) {
                if (type.value == value) return type
            }
            return null
        }
    }
}
