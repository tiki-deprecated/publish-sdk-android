/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mytiki.tiki_sdk_android.TikiSdk

@Composable
fun BottomSheet(
    isShowing: Boolean,
    onDismiss: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    var offsetY by remember { mutableStateOf(0f) }

    DisposableEffect(Unit) {
        onDispose {
            onDismiss?.invoke()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onPress = { offsetY = 0f })
            }
            .offset(y = offsetY.dp)
            .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
            .background(color = TikiSdk.theme(context).primaryBackgroundColor)
    ) {
        Box(
            modifier = Modifier
                .padding(top = 20.dp)
                .offset(y = offsetY.dp)
        ) {
            content()
        }
    }

    if (isShowing) {
        LaunchedEffect(Unit) {
            offsetY = 0f
        }
    } else {
        LaunchedEffect(Unit) {
            offsetY = -40f
        }
    }
}


