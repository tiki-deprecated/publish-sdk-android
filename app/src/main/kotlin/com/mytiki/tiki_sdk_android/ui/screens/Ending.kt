/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mytiki.tiki_sdk_android.TikiSdk

@Composable
fun Ending(title: @Composable () -> Unit, message: String, footnote: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(TikiSdk.theme(LocalContext.current).primaryBackgroundColor),
    ) {
        title()
        Text(
            text = message,
            modifier = Modifier
                .padding(vertical = 36.dp)
                .heightIn(min = 0.dp),
            color = TikiSdk.theme(LocalContext.current).primaryBackgroundColor
        )
        footnote()
    }
}
