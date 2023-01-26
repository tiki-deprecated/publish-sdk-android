/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.tiki_platform_channel

/**
 * TIKI SDK Flutter methods.
 *
 * @constructor Create empty Method enum
 */
enum class MethodEnum {
    BUILD {
        override val methodCall: String = "build"
    },
    ASSIGN_OWNERSHIP {
        override val methodCall: String = "assignOwnership"
    },
    GET_OWNERSHIP {
        override val methodCall: String = "getOwnership"
    },
    MODIFY_CONSENT {
        override val methodCall: String = "modifyConsent"
    },
    GET_CONSENT {
        override val methodCall: String = "getConsent"
    },
    APPLY_CONSENT {
        override val methodCall: String = "applyConsent"
    };

    abstract val methodCall: String
}