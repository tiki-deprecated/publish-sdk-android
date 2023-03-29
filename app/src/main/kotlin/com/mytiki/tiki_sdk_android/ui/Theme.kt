package com.mytiki.tiki_sdk_android.ui

import androidx.compose.ui.graphics.Color
import com.mytiki.tiki_sdk_android.TikiSdk


/**
 * Controls the UI theming for TikiSdk.
 */
class Theme(
    dark: Boolean = true,
    private var primaryTextColor: Color = if (dark) Color(0xFFF6F6F6) else Color(0xFF1C0000),
    private var primaryBackgroundColor: Color = if (dark) {
        Color(0xFF1C1C1E)
    } else {
        Color(0xFF1C1C1E)
    },
    private var secondaryBackgroundColor: Color = if (dark) {
        Color(0xFFF6F6F6).copy(alpha = 0.38f)
    } else {
        Color(0xFFF6F6F6)
    },
    private var accentColor: Color = if (dark) Color(0xFF00B272) else Color(0xFF00B272),
    private var fontFamily: String = "SpaceGrotesk"
) {

    /**
     * Primary text color. Used in the default text items.
     */
    val primaryTextColorValue: Color
        get() = primaryTextColor

    /**
     * Secondary text color. Used in specific text items.
     *
     * Defaults to [primaryTextColor] with 60% alpha transparency.
     */
    val secondaryTextColorValue: Color
        get() = primaryTextColor.copy(alpha = 0.6f)

    /**
     * Primary background color. The default color for backgrounds.
     */
    val primaryBackgroundColorValue: Color
        get() = primaryBackgroundColor

    /**
     * Secondary background color.
     */
    val secondaryBackgroundColorValue: Color
        get() = secondaryBackgroundColor

    /**
     * Accent color. Used to decorate or highlight items.
     */
    val accentColorValue: Color
        get() = accentColor

    /**
     * The default font family for all texts.
     */
    val fontFamilyValue: String
        get() = fontFamily

    /**
     * Set primary text color
     *
     * @param primaryTextColor
     * @return this Theme
     */
    fun setPrimaryTextColor(primaryTextColor: Color): Theme {
        this.primaryTextColor = primaryTextColor
        return this
    }

    /**
     * Set primary background color
     *
     * @param primaryBackgroundColor
     * @return this Theme
     */
    fun setPrimaryBackgroundColor(primaryBackgroundColor: Color): Theme {
        this.primaryBackgroundColor = primaryBackgroundColor
        return this
    }

    /**
     * Set secondary background color
     *
     * @param secondaryBackgroundColor
     * @return this Theme
     */
    fun setSecondaryBackgroundColor(secondaryBackgroundColor: Color): Theme {
        this.secondaryBackgroundColor = secondaryBackgroundColor
        return this
    }

    /**
     * Set accent color
     *
     * @param accentColor
     * @return this Theme
     */
    fun setAccentColor(accentColor: Color): Theme {
        this.accentColor = accentColor
        return this
    }

    /**
     * Set font family
     *
     * @param fontFamily
     * @return this Theme
     */
    fun setFontFamily(fontFamily: String): Theme {
        this.fontFamily = fontFamily
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
