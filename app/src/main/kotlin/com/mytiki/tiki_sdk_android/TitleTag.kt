/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android

import com.squareup.moshi.JsonClass

/**
 * Use case for license.
 */
@JsonClass(generateAdapter = true)
class TitleTag(value: String) {

    var value: String
        private set

    init {
        try {
            val titleTagEnum = TitleTagEnum.fromValue(value)
            this.value = titleTagEnum.value
        } catch (e: Exception) {
            this.value = "custom:$value"
        }
    }

    constructor(titleTagEnum: TitleTagEnum) : this(titleTagEnum.value)

    companion object {
        val EMAIL_ADDRESS = TitleTag(TitleTagEnum.EMAIL_ADDRESS)
        val PHONE_NUMBER = TitleTag(TitleTagEnum.PHONE_NUMBER)
        val PHYSICAL_ADDRESS = TitleTag(TitleTagEnum.PHYSICAL_ADDRESS)
        val CONTACT_INFO = TitleTag(TitleTagEnum.CONTACT_INFO)
        val HEALTH = TitleTag(TitleTagEnum.HEALTH)
        val FITNESS = TitleTag(TitleTagEnum.FITNESS)
        val PAYMENT_INFO = TitleTag(TitleTagEnum.PAYMENT_INFO)
        val CREDIT_INFO = TitleTag(TitleTagEnum.CREDIT_INFO)
        val FINANCIAL_INFO = TitleTag(TitleTagEnum.FINANCIAL_INFO)
        val PRECISE_LOCATION = TitleTag(TitleTagEnum.PRECISE_LOCATION)
        val COARSE_LOCATION = TitleTag(TitleTagEnum.COARSE_LOCATION)
        val SENSITIVE_INFO = TitleTag(TitleTagEnum.SENSITIVE_INFO)
        val CONTACTS = TitleTag(TitleTagEnum.CONTACTS)
        val MESSAGES = TitleTag(TitleTagEnum.MESSAGES)
        val PHOTO_VIDEO = TitleTag(TitleTagEnum.PHOTO_VIDEO)
        val AUDIO = TitleTag(TitleTagEnum.AUDIO)
        val GAMEPLAY_CONTENT = TitleTag(TitleTagEnum.GAMEPLAY_CONTENT)
        val CUSTOMER_SUPPORT = TitleTag(TitleTagEnum.CUSTOMER_SUPPORT)
        val USER_CONTENT = TitleTag(TitleTagEnum.USER_CONTENT)
        val BROWSING_HISTORY = TitleTag(TitleTagEnum.BROWSING_HISTORY)
        val SEARCH_HISTORY = TitleTag(TitleTagEnum.SEARCH_HISTORY)
        val USER_ID = TitleTag(TitleTagEnum.USER_ID)
        val DEVICE_ID = TitleTag(TitleTagEnum.DEVICE_ID)
        val PURCHASE_HISTORY = TitleTag(TitleTagEnum.PURCHASE_HISTORY)
        val PRODUCT_INTERACTION = TitleTag(TitleTagEnum.PRODUCT_INTERACTION)
        val ADVERTISING_DATA = TitleTag(TitleTagEnum.ADVERTISING_DATA)
        val USAGE_DATA = TitleTag(TitleTagEnum.USAGE_DATA)
        val CRASH_DATA = TitleTag(TitleTagEnum.CRASH_DATA)
        val PERFORMANCE_DATA = TitleTag(TitleTagEnum.PERFORMANCE_DATA)
        val DIAGNOSTIC_DATA = TitleTag(TitleTagEnum.DIAGNOSTIC_DATA)
    }

}
