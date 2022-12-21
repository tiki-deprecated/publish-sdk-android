package com.mytiki.tiki_sdk_android

import com.squareup.moshi.JsonClass

/**
 * The type of data origin for an ownership registry.
 */
@JsonClass(generateAdapter = false)
enum class TikiSdkDataTypeEnum {
    data_point, data_stream, data_pool
}