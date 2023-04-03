/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.ui.elements

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mytiki.tiki_sdk_android.TikiSdk

@Composable
fun TikiSdkButton(
    text: String,
    onTap: () -> Unit,
    textColor: Color = TikiSdk.theme(LocalContext.current).primaryTextColor,
    backgroundColor: Color = TikiSdk.theme(LocalContext.current).accentColor,
    borderColor: Color = TikiSdk.theme(LocalContext.current).accentColor,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp)
            .background(backgroundColor, shape = RoundedCornerShape(10.dp))
            .clickable {
                onTap()
            }
            .border(BorderStroke(1.dp, borderColor), shape = RoundedCornerShape(10.dp))
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = textColor,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
        )
    }
}
