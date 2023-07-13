/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.ui

/**
 * A representation of data usage permission.
 *
 * @constructor Creates a new bullet item with the given description and usage status.
 * @param text A string that describes the usage of the data.
 * @param isUsed A boolean value that indicates whether the data is being used.
 */
data class Bullet(
    /**
     * A string that describes the usage of the data.
     */
    val text: String,
    /**
     * A boolean value that indicates whether the data is being used.
     */
    val isUsed: Boolean
)