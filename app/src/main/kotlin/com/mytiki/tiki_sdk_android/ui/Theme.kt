/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.mytiki.tiki_sdk_android.R
import com.mytiki.tiki_sdk_android.TikiSdk

/**
 * Controls the UI theming for TikiSdk.
 */
class Theme(
    dark: Boolean = true,
    private var _primaryTextColor: Color = if (dark) Color(0xFFF6F6F6) else Color(0xFF1C0000),
    private var _primaryBackgroundColor: Color = if (dark) {
        Color(0xFF1C1C1E)
    } else {
        Color(0xFF1C1C1E)
    },
    private var _secondaryBackgroundColor: Color = if (dark) {
        Color(0xFFF6F6F6).copy(alpha = 0.38f)
    } else {
        Color(0xFFF6F6F6)
    },
    private var _accentColor: Color = if (dark) Color(0xFF00B272) else Color(0xFF00B272),
    private var _fontFamily: Int = R.font.space_grotesk
) {

    val font: FontFamily = FontFamily(Font(fontFamily))

    /**
     * Back arrow image, used in the `NavigationHeader` to dismiss the view.
     */
    val backArrow = R.drawable.back_arrow

    /**
     * Check icon, used in the `UsedFor` list for bullets.
     */
    val checkIcon = R.drawable.check_icon

    /**
     * Question icon, used for the `LeanMoreButton`.
     */
    val questionIcon = R.drawable.question_icon

    /**
     * Red X icon, used in the `UsedFor` list for bullets.
     */
    val xIcon = R.drawable.x_icon

    /**
     * Primary text color. Used in the default text items.
     */
    val primaryTextColor: Color
        get() = _primaryTextColor

    /**
     * Secondary text color. Used in specific text items.
     *
     * Defaults to [primaryTextColor] with 60% alpha transparency.
     */
    val secondaryTextColor: Color
        get() = _primaryTextColor.copy(alpha = 0.6f)

    /**
     * Primary background color. The default color for backgrounds.
     */
    val primaryBackgroundColor: Color
        get() = _primaryBackgroundColor

    /**
     * Secondary background color.
     */
    val secondaryBackgroundColor: Color
        get() = _secondaryBackgroundColor

    /**
     * Accent color. Used to decorate or highlight items.
     */
    val accentColor: Color
        get() = _accentColor

    /**
     * The default font family for all texts.
     */
    val fontFamily: Int
        get() = _fontFamily

    /**
     * Set primary text color
     *
     * @param primaryTextColor
     * @return this Theme
     */
    fun setPrimaryTextColor(primaryTextColor: Color): Theme {
        _primaryTextColor = primaryTextColor
        return this
    }

    /**
     * Set primary background color
     *
     * @param primaryBackgroundColor
     * @return this Theme
     */
    fun setPrimaryBackgroundColor(primaryBackgroundColor: Color): Theme {
        _primaryBackgroundColor = primaryBackgroundColor
        return this
    }

    /**
     * Set secondary background color
     *
     * @param secondaryBackgroundColor
     * @return this Theme
     */
    fun setSecondaryBackgroundColor(secondaryBackgroundColor: Color): Theme {
        _secondaryBackgroundColor = secondaryBackgroundColor
        return this
    }

    /**
     * Set accent color
     *
     * @param accentColor
     * @return this Theme
     */
    fun setAccentColor(accentColor: Color): Theme {
        _accentColor = accentColor
        return this
    }

    /**
     * Set font family
     *
     * @param fontFamily
     * @return this Theme
     */
    fun setFontFamily(fontFamily: Int): Theme {
        _fontFamily = fontFamily
        return this
    }

    /**
     * Returns the parent TikiSdk
     *
     * @return TikiSdk
     */
    fun and(): TikiSdk {
        return TikiSdk
    }


}
