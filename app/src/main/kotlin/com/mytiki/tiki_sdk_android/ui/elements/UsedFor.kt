/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mytiki.tiki_sdk_android.TikiSdk
import com.mytiki.tiki_sdk_android.ui.Bullet

@Composable
fun UsedFor(bullets: List<Bullet>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "HOW YOUR DATA WILL BE USED",
            color = TikiSdk.theme(LocalContext.current).primaryTextColor,
            fontFamily = TikiSdk.theme(LocalContext.current).font,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        for (item in bullets) {
            BulletItem(item = item)
        }
    }
}

@Composable
fun BulletItem(item: Bullet) {
    val icon = if (item.isUsed) {
        painterResource(id = TikiSdk.theme(LocalContext.current).checkIcon)
    } else {
        painterResource(id = TikiSdk.theme(LocalContext.current).checkIcon)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        Image(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.padding(end = 16.dp)
        )
        Text(
            text = item.text,
            color = TikiSdk.theme(LocalContext.current).primaryTextColor,
            fontFamily = TikiSdk.theme(LocalContext.current).font,
        )
    }
}
