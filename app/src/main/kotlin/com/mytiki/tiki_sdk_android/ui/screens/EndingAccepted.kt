package com.mytiki.tiki_sdk_android.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mytiki.tiki_sdk_android.TikiSdk
import com.mytiki.tiki_sdk_android.ui.elements.YourChoice

@Composable
fun EndingAccepted(onSettings: () -> Unit, dismiss: () -> Unit) {
    Ending(
        title = { YourChoice() },
        message = "Awesome! You’re in",
        footnote = {
            Column(modifier = Modifier.padding(bottom = 50.dp)) {
                Text(
                    text = "We’re on it, stay tuned.",
                    color = TikiSdk.theme(LocalContext.current).secondaryTextColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    Text(
                        text = "Change your mind anytime in ",
                        color = TikiSdk.theme(LocalContext.current).secondaryTextColor,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        text = "settings.",
                        color = TikiSdk.theme(LocalContext.current).secondaryTextColor,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier
                            .padding(start = 2.dp)
                            .clickable { onSettings(); dismiss() }
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun EndingAcceptedPreview() {
    EndingAccepted(onSettings = { }, dismiss = { })
}
