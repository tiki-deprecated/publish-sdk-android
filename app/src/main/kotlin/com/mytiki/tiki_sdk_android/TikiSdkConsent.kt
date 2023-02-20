/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android

import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class TikiSdkConsent(
    /**
     * Transaction ID corresponding to the ownership mint for the data source.
     */
    val ownershipId: String,

    /**
     *  The identifier of the paths and use cases for this consent.
     */
    val destination: TikiSdkDestination,

    /**
     *  The transaction id of this registry in the blockchain.
     */
    val transactionId: String,

    /**
     *  Optional description of the consent.
     */
    val about: String? = null,

    /**
     *  Optional reward description the user will receive for this consent.
     */
    val reward: String? = null,

    /**
     *  The Consent expiration. Null for no expiration.
     */
    val expiry: Date? = null
)