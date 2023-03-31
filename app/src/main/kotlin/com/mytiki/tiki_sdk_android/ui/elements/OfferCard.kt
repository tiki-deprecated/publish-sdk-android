package com.mytiki.tiki_sdk_android.ui.elements

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mytiki.tiki_sdk_android.TikiSdk
import com.mytiki.tiki_sdk_android.ui.Offer

@Composable
fun OfferCard(offer: Offer) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .background(TikiSdk.theme(context).primaryBackgroundColor)
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp, bottom = 32.dp)
            .clip(RoundedCornerShape(10.dp))
            .shadow(
                ambientColor = Color.Black.copy(alpha = 0.05f),
                elevation = 4.dp
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        offer.reward?.let {
            val bitmap =
                Bitmap.createBitmap(it.intrinsicWidth, it.intrinsicHeight, Bitmap.Config.ARGB_8888)
            it.setBounds(
                0,
                0,
                it.intrinsicWidth,
                it.intrinsicHeight
            ) // use intrinsicWidth and intrinsicHeight
            it.draw(Canvas(bitmap))
            Image(bitmap = bitmap.asImageBitmap(), contentDescription = "Offer Reward")
        }
        Text(
            text = offer.description ?: "",
            fontSize = 16.sp,
            fontFamily = TikiSdk.theme(context).font,
            color = TikiSdk.theme(context).secondaryTextColor,
            maxLines = 3,
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
    }
}