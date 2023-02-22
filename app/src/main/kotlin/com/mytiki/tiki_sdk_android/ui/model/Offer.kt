package com.mytiki.tiki_sdk_android.ui.model

import android.graphics.drawable.Drawable

/**
 * Offer
 *
 * The definition of the offer for data usage.
 *
 * @property description
 * @property image
 * @constructor Create empty Offer
 */
data class Offer(
    /**
     * A description of the data usage.
     *
     * It will occupy 3 lines maximum in the UI. An ellipsis will be added on overflow.
     * Accepts basic markdown styles: underline, bold and italic.
     */
    val description: String,

    /**
     * A image description of the data usage.
     *
     * Use a 320x100 aspect ratio image.
     */
    val image: Drawable,

    /**
     * The list of items that describes what can be done with the user data.
     *
     * Maximum 3 items.
     */
    val items: ArrayList<OfferItem>
)