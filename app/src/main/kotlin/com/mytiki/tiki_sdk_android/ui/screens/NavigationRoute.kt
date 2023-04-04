/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mytiki.tiki_sdk_android.TikiSdk
import com.mytiki.tiki_sdk_android.ui.elements.NavigationHeader

@Composable
fun NavigationRoute(
    isShowing: Boolean,
    title: String,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    var offsetX by remember { mutableStateOf(0f) }
    val context = LocalContext.current
    if (isShowing) {
        val offset by animateFloatAsState(
            targetValue = if (isShowing) 0f else 1000f,
            animationSpec = tween(durationMillis = 300)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(TikiSdk.theme(context).secondaryBackgroundColor)
                .offset(x = offsetX.dp, y = 0.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .offset(x = offset.dp, y = 0.dp)
                    .align(Alignment.TopCenter)
            ) {
                NavigationHeader(title = title, onBackPressed = onDismiss)
                content()
            }
        }
    }

    // Detect user drag on screen to dismiss
    Box(
        modifier = Modifier
            .fillMaxSize()
            .offset(x = offsetX.dp, y = 0.dp)
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    if (delta < -100) {
                        onDismiss()
                    }
                    offsetX += delta
                }
            )
    )
}
