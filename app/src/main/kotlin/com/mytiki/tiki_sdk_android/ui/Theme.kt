/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.ui

import android.graphics.Color
import androidx.annotation.ColorInt
import com.mytiki.tiki_sdk_android.R
import com.mytiki.tiki_sdk_android.TikiSdk

/**
 * Controls the UI theming for TikiSdk.
 */
class Theme {

    /**
     * Primary text color. Used in default text items.
     */
    @ColorInt
    var primaryTextColor: Int = Color.BLACK

    /**
     * Secondary text color. Used in specific text items. Defaults to [primaryTextColor] with 60% alpha transparency.
     */
    @ColorInt
    var secondaryTextColor: Int = Color.argb((255 * 0.6).toInt(), 0, 0, 0)

    /**
     * Primary background color. The default color for backgrounds.
     */
    @ColorInt
    var primaryBackgroundColor: Int = Color.WHITE

    /**
     * Secondary background color.
     */
    @ColorInt
    var secondaryBackgroundColor: Int = Color.rgb(245, 245, 245)

    /**
     * Accent color. Used to decorate or highlight items.
     */
    @ColorInt
    var accentColor: Int = Color.rgb(0, 179, 112)

    /**
     * The `fontFamily` Regular variation.
     */
    var fontRegular: Int = R.font.space_grotesk_regular
        private set

    /**
     * The `fontFamily` Bold variation.
     */
    var fontBold: Int = R.font.space_grotesk_bold
        private set

    /**
     * The `fontFamily` Light variation.
     */
    var fontLight: Int = R.font.space_grotesk_light
        private set

    /**
     * The `fontFamily` Medium variation.
     */
    var fontMedium: Int = R.font.space_grotesk_medium
        private set

    /**
     * The `fontFamily` SemiBold variation.
     */
    var fontSemiBold: Int = R.font.space_grotesk_semibold
        private set

    // Instance Methods
    /**
     * Sets the primary text color.
     *
     * @param primaryTextColor The color for default text items.
     * @return This [Theme] instance for method chaining.
     */
    fun primaryTextColor(primaryTextColor: Int): Theme {
        this.primaryTextColor = primaryTextColor
        return this
    }

    /**
     * Sets the secondary text color.
     *
     * @param secondaryTextColor The color for specific text items.
     * @return This [Theme] instance for method chaining.
     */
    fun secondaryTextColor(secondaryTextColor: Int): Theme {
        this.secondaryTextColor = secondaryTextColor
        return this
    }

    /**
     * Sets the primary background color.
     *
     * @param primaryBackgroundColor The default color for backgrounds.
     * @return This [Theme] instance for method chaining.
     */
    fun primaryBackgroundColor(primaryBackgroundColor: Int): Theme {
        this.primaryBackgroundColor = primaryBackgroundColor
        return this
    }

    /**
     * Sets the secondary background color.
     *
     * @param secondaryBackgroundColor The color for secondary backgrounds.
     * @return This [Theme] instance for method chaining.
     */
    fun secondaryBackgroundColor(secondaryBackgroundColor: Int): Theme {
        this.secondaryBackgroundColor = secondaryBackgroundColor
        return this
    }

    /**
     * Sets the accent color.
     *
     * @param accentColor The color for decorating or highlighting items.
     * @return This [Theme] instance for method chaining.
     */
    fun accentColor(accentColor: Int): Theme {
        this.accentColor = accentColor
        return this
    }

    /**
     * Sets the font family variations.
     *
     * @param fontRegular The font resource for Regular font family variation.
     * @param fontBold The font resource for Bold font family variation.
     * @param fontLight The font resource for Light font family variation.
     * @param fontMedium The font resource for Medium font family variation.
     * @param fontSemiBold The font resource for SemiBold font family variation.
     * @return This [Theme] instance for method chaining.
     */
    fun fontFamily(
        fontRegular: Int,
        fontBold: Int,
        fontLight: Int,
        fontMedium: Int,
        fontSemiBold: Int
    ) {
        this.fontRegular = fontRegular
        this.fontBold = fontBold
        this.fontLight = fontLight
        this.fontMedium = fontMedium
        this.fontSemiBold = fontSemiBold
    }

    /**
     * Returns the [TikiSdk] instance to simplify the chaining of methods in SDK configuration and initialization.
     */
    fun and(): TikiSdk {
        return TikiSdk
    }
}

