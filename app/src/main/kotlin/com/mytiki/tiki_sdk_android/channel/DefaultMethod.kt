/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.channel

enum class DefaultMethod(private val value: String) : ChannelMethod {
    INITIALIZE("initialize");

    override fun value(): String {
        return this.value
    }
}