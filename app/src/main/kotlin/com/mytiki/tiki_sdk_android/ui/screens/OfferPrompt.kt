package com.mytiki.tiki_sdk_android.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mytiki.tiki_sdk_android.TikiSdk
import com.mytiki.tiki_sdk_android.ui.Offer
import com.mytiki.tiki_sdk_android.ui.elements.*


@Composable
fun OfferPrompt(
    currentOffer: Offer,
    offers: Map<String, Offer>,
    title: @Composable () -> Unit = { TradeYourData() },
    backgroundColor: Color? = null,
    accentColor: Color? = null,
    onAccept: (Offer) -> Unit,
    onDecline: (Offer) -> Unit,
    onLearnMore: () -> Unit
) {
    val context: Context = LocalContext.current
    BottomSheet(
        isShowing = true
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(vertical = 32.dp)
                    .fillMaxWidth()
            ) {
                title()
                Row(
                    modifier = Modifier.align(alignment = Alignment.CenterEnd)
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    LearnMoreButton(onTap = onLearnMore)
                }
            }
            OfferCard(currentOffer)
            UsedFor(bullets = currentOffer.bullets)
            Row(
                modifier = Modifier
                    .padding(bottom = 50.dp)
                    .fillMaxWidth()
            ) {
                TikiSdkButton(
                    text = "Back Off",
                    onTap = { onDecline(currentOffer) },
                    textColor = TikiSdk.theme(context).primaryTextColor,
                    borderColor = accentColor ?: TikiSdk.theme(context).accentColor,
                )
                TikiSdkButton(
                    text = "I'm in",
                    onTap = { onAccept(currentOffer) },
                    textColor = TikiSdk.theme(context).primaryTextColor,
                    borderColor = accentColor ?: TikiSdk.theme(context).accentColor,
                )
            }
        }
    }
}
