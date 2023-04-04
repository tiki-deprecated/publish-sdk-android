/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mytiki.tiki_sdk_android.TikiSdk

@Composable
fun NavigationHeader(
    title: String,
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 20.dp)
    ) {
        Image(
            painter = painterResource(id = TikiSdk.theme(context).backArrow),
            contentDescription = "Back",
            modifier = Modifier.clickable { onBackPressed() }
        )
        Text(
            text = title,
            fontSize = 20.sp,
            fontFamily = TikiSdk.theme(context).font,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )
    }
}
