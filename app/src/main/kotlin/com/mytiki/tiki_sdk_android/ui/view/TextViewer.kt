package com.mytiki.tiki_sdk_android.ui.view

import android.content.Context
import android.graphics.Color
import android.graphics.fonts.FontFamily
import android.util.AttributeSet
import android.widget.LinearLayout

/**
 * A MarkDown text viewer with optional callback button.
 *
 * @property accentColor The color that will be used in the Color Button. Default #24956A.
 * @property primaryColor The text color. Default #2D2D2D.
 * @property backgroundColor default #FFFFFF.
 * @property text The text to be shown in the viewer.
 * @property fontFamily The font family of the text.
 * @property buttonText The button's label.
 * @property callback The button's on tap callback. If null the button will not be displayed.
 * @constructor
 *
 * @param context
 */
class TextViewer(
    context: Context,
    attrs: AttributeSet,
    /**
     * The color that will be used in the color Button. 
     */
    val accentColor: Color,

    /**
     * The text color.
     */
    val primaryColor: Color,

    /**
     * The color used in background and in color Button text.
     */
    val backgroundColor: Color,

    /**
     * The text to be shown in the viewer.
     */
    val text: String,

    /**
     * The fontFamily for the text.
     */
    val fontFamily: FontFamily,

    /**
     * The button's label.
     */
    val buttonText: String,

    /**
     * The callback function to the button tap.
     * 
     * If null, the button will not be shown.
     */
    val callback: () -> Void
    ) : LinearLayout(context, attrs){
}