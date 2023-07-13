/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.trail

class Tag private constructor(val value: String) {
    constructor(tag: TagCommon) : this(tag.value)

    companion object {
        fun custom(tag: String): Tag {
            return Tag("custom:$tag")
        }

        fun from(tag: String): Tag {
            val common: TagCommon? = TagCommon.from(tag)
            return if (common != null) Tag(common) else custom(tag)
        }
    }
}