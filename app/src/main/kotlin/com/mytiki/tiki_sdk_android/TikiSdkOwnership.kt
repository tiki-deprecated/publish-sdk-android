package com.mytiki.tiki_sdk_android

import com.squareup.moshi.JsonClass

/**
 * The Ownership NFT.
 *
 * The registry of ownership to a given data point, pool, or stream.
 */
@JsonClass(generateAdapter = true)
class TikiSdkOwnership(

    /**
     * The identification of the source.
     */
    val source: String,

    /**
     * The type of the data source: data point, pool or stream.
     */
    val type: TikiSdkDataTypeEnum,

    /**
     * The origin from which the data was generated.
     */
    val origin: String,

    /**
     * The transaction id of this registry.
     */
    val transactionId: String,

    /**
     * The kinds of data this contains.
     */
    val contains: List<String> = listOf(),

    /**
     * The description about the data.
     */
    val about: String? = null,

    )
