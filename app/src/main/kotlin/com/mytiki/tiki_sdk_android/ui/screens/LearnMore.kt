/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mytiki.tiki_sdk_android.TikiSdk

@Composable
fun LearnMore() {
    val learnMoreText =
        LocalContext.current.assets.open("learnMore.md").bufferedReader().use { it.readText() }
    val context = LocalContext.current
    LazyColumn {
        item {
            Text(
                text = learnMoreText,
                fontSize = 16.sp,
                fontFamily = TikiSdk.theme(context).font,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            )
        }
    }
}
