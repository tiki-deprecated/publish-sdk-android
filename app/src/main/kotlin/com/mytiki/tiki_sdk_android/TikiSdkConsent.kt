package com.mytiki.tiki_sdk_android

import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

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
     *  The transaction id of this registry.
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
    val expiry: LocalDateTime? = null
)