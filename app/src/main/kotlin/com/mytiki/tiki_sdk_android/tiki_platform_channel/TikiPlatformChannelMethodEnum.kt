package com.mytiki.tiki_sdk_android.tiki_platform_channel

import com.mytiki.tiki_sdk_android.tiki_platform_channel.req.*
import com.mytiki.tiki_sdk_android.tiki_platform_channel.rsp.RspBuild
import com.mytiki.tiki_sdk_android.tiki_platform_channel.rsp.RspConsentGet
import com.mytiki.tiki_sdk_android.tiki_platform_channel.rsp.RspOwnership
import java.lang.reflect.Type

enum class TikiPlatformChannelMethodEnum {
    BUILD {
        override val reqType: Type = ReqBuild::class.java
        override val rspType: Type = RspBuild::class.java
        override val methodCall: String = "build"
    },
    ASSIGN_OWNERSHIP {
        override val reqType: Type = ReqOwnershipAssign::class.java
        override val rspType: Type = RspOwnership::class.java
        override val methodCall: String = "assignOwnership"
    },
    GET_OWNERSHIP {
        override val reqType: Type = ReqOwnershipGet::class.java
        override val rspType: Type = RspOwnership::class.java
        override val methodCall: String = "getOwnership"
    },
    MODIFY_CONSENT {
        override val reqType: Type = ReqConsentModify::class.java
        override val rspType: Type = RspConsentGet::class.java
        override val methodCall: String = "modifyConsent"
    },
    GET_CONSENT {
        override val reqType: Type = ReqConsentGet::class.java
        override val rspType: Type = RspConsentGet::class.java
        override val methodCall: String = "getConsent"
    },
    APPLY_CONSENT {
        override val reqType: Type = ReqBuild::class.java
        override val rspType: Type = ReqBuild::class.java
        override val methodCall: String = "applyConsent"
    };

    abstract val reqType: Type
    abstract val rspType: Type
    abstract val methodCall: String
}