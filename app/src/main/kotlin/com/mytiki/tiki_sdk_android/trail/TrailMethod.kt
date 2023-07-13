/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.trail

import com.mytiki.tiki_sdk_android.channel.ChannelMethod

enum class TrailMethod(private val value: String) : ChannelMethod {
    IS_INITIALIZED("trail.isInitialized"),
    ADDRESS("trail.address"),
    ID("trail.id"),
    GUARD("trail.guard"),
    TITLE_CREATE("trail.title.create"),
    TITLE_GET("trail.title.get"),
    TITLE_ID("trail.title.id"),
    LICENSE_CREATE("trail.license.create"),
    LICENSE_ALL("trail.license.all"),
    LICENSE_GET("trail.license.get"),
    PAYABLE_CREATE("trail.payable.create"),
    PAYABLE_ALL("trail.payable.all"),
    PAYABLE_GET("trail.payable.get"),
    RECEIPT_CREATE("trail.receipt.create"),
    RECEIPT_ALL("trail.receipt.all"),
    RECEIPT_GET("trail.receipt.get");

    override fun value(): String {
        return this.value
    }
}