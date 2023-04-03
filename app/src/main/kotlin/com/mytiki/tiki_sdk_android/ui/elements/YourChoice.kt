/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.ui.elements

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.mytiki.tiki_sdk_android.TikiSdk

@Composable
fun YourChoice() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "YOUR",
            fontFamily = TikiSdk.theme(LocalContext.current).font,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "CHOICE",
            fontFamily = TikiSdk.theme(LocalContext.current).font,
            fontWeight = FontWeight.Bold,
            color = TikiSdk.theme(LocalContext.current).accentColor
        )
    }
}
