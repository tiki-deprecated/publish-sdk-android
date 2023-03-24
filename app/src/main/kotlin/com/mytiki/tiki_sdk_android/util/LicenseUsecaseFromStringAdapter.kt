/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.util

import com.mytiki.tiki_sdk_android.LicenseUse
import com.mytiki.tiki_sdk_android.LicenseUsecase
import com.squareup.moshi.*

import java.util.*

/**
 * Single value to String JSON adapter for Moshi.
 *
 * @constructor Create empty Time stamp to date adapter
 */
class LicenseUsecaseFromStringAdapter : JsonAdapter<LicenseUsecase>() {

    @FromJson
    override fun fromJson(reader: JsonReader): LicenseUsecase? {
        if (reader.peek() == JsonReader.Token.NULL) return reader.nextNull()
        return try {
            val value = reader.nextString()
            LicenseUsecase(value)
        } catch (e: Exception) {
            null
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, licenseUsecase: LicenseUsecase?) {
        writer.value(licenseUsecase?.value)
    }
}