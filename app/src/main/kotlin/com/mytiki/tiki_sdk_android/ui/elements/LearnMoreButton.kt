package com.mytiki.tiki_sdk_android.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mytiki.tiki_sdk_android.TikiSdk

@Composable
fun LearnMoreButton(
    onTap: (() -> Unit)? = null
) {
    val context = LocalContext.current
    Button(
        onClick = { onTap?.invoke() },
        modifier = Modifier
            .size(24.dp)
            .background(Color.Transparent)
    ) {
        Image(
            painter = painterResource(id = TikiSdk.theme(context).questionIcon),
            contentDescription = null
        )
    }
}
