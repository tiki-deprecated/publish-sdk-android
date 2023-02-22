package com.mytiki.tiki_sdk_android.ui.view

import android.content.Context
import android.graphics.Color
import android.graphics.fonts.FontFamily
import com.google.android.material.bottomsheet.BottomSheetDialog

/**
 * Awesome completion sheet
 *
 * A dismissible bottom sheet that will be shown after the user accepts sharing its data.
 *
 * @property accentColor
 * @property primaryColor
 * @property backgroundColor
 * @property font
 * @constructor
 *
 * @param context
 */
class AwesomeCompletionSheet(
  context: Context,
  /**
   * The color that will be used in "your".
   */
  val accentColor : Color,

  /**
   * The text color.
   */
  val primaryColor: Color,

  /**
   * The color used in all backgrounds.
   */
  val backgroundColor: Color,

  /**
   * The fontFamily from pubspec.
   */
  val font: FontFamily
): BottomSheetDialog(context){}