/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.ui.screens

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mytiki.tiki_sdk_android.ui.Permission
import com.mytiki.tiki_sdk_android.ui.elements.YourChoice
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async

@Composable
fun EndingError(
    pendingPermissions: MutableState<List<Permission>?>,
    onAuthorized: () -> List<Permission>
) {
    val context = LocalContext.current
    LaunchedEffect(pendingPermissions.value) {
        requestPendingPermissions(context, pendingPermissions.value.orEmpty()) {
            pendingPermissions.value ?: mutableListOf()
        }
    }
    Ending(
        title = { YourChoice() },
        message = "Permission Required",
        footnote = {
            Column {
                Text(
                    text = "Offer declined.",
                    fontSize = 18.sp,
                    color = Color.Black.copy(alpha = 0.6f),
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                Row {
                    Text(
                        text = "To proceed, allow ",
                        fontSize = 18.sp,
                        color = Color.Black.copy(alpha = 0.6f),
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        text = getPendingPermissionsNames(pendingPermissions.value),
                        fontSize = 18.sp,
                        color = Color.Black.copy(alpha = 0.6f),
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.clickable {
                            requestPendingPermissions(
                                context,
                                pendingPermissions.value!!,
                                onAuthorized
                            )
                        }
                    )
                }
            }
        }
    )
}

@Composable
fun getPendingPermissionsNames(pendingPermissions: List<Permission>?): String {
    return if (pendingPermissions?.size == 1) {
        pendingPermissions[0].name
    } else {
        pendingPermissions?.joinToString(separator = ", ") { it.name } ?: ""
    }
}

fun requestPendingPermissions(
    context: Context,
    pendingPermissions: List<Permission>,
    onAuthorized: () -> List<Permission>
) {
    if (pendingPermissions.isEmpty()) {
        onAuthorized()
        return
    }
    pendingPermissions[0].requestAuth(context) { isAuthorized: Boolean ->
        if (isAuthorized) {
            MainScope().async {
                requestPendingPermissions(context, pendingPermissions.drop(1)) {
                    pendingPermissions
                }
            }
        }
    }
}
