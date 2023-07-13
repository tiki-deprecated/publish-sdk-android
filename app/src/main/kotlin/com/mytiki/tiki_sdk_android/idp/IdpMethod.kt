/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.idp

import com.mytiki.tiki_sdk_android.channel.ChannelMethod

enum class IdpMethod(private val value: String) : ChannelMethod {
    IS_INITIALIZED("idp.isInitialized"),
    KEY("idp.key"),
    EXPORT("idp.export"),
    IMPORT("idp.import"),
    SIGN("idp.sign"),
    VERIFY("idp.verify"),
    TOKEN("idp.token");

    override fun value(): String {
        return this.value
    }
}