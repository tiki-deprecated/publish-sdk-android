/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mytiki.tiki_sdk_android.TikiSdk
import com.mytiki.tiki_sdk_android.ui.elements.YourChoice

@Composable
fun EndingDeclined(onSettings: () -> Unit, dismiss: () -> Unit) {
    val theme = TikiSdk.theme(LocalContext.current)

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        YourChoice()
        Text(
            text = "Backing Off",
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            color = theme.primaryTextColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 36.dp, bottom = 36.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 50.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Your data is valuable.",
                fontWeight = FontWeight.Light,
                fontSize = 18.sp,
                color = theme.secondaryTextColor
            )
        }
    }
}
