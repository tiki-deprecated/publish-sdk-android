/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.util

import com.mytiki.tiki_sdk_android.TitleTag
import com.squareup.moshi.*

/**
 * Single value to String JSON adapter for Moshi.
 *
 * @constructor Create empty Time stamp to date adapter
 */
class TitleTagFromStringAdapter : JsonAdapter<TitleTag>() {

    @FromJson
    override fun fromJson(reader: JsonReader): TitleTag? {
        if (reader.peek() == JsonReader.Token.NULL) return reader.nextNull()
        return try {
            val value = reader.nextString()
            TitleTag(value)
        } catch (e: Exception) {
            null
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, titleTag: TitleTag?) {
        writer.value(titleTag?.value)
    }
}