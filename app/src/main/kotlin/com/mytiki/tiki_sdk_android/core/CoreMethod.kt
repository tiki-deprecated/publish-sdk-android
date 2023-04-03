/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.core

/// The type of data origin for an ownership registry.
enum class CoreMethod(val value: String) {
    BUILD("build"),
    LICENSE("license"),
    LATEST("latest"),
    ALL("all"),
    GET_LICENSE("getLicense"),
    TITLE("title"),
    GET_TITLE("getTitle"),
    GUARD("guard")
}