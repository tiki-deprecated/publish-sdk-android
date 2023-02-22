package com.mytiki.tiki_sdk_android.ui.model

/**
 * Offer item
 *
 * An item that describes what can be done with the user data.
 *
 * @property description
 * @property allow
 * @constructor Create empty Offer item
 */
data class OfferItem(

    /**
     * Description of the data usage.
     */
    val description:  String,

    /**
     * Whether this usage is allowed or not.
     */
    val allow: Boolean,
)