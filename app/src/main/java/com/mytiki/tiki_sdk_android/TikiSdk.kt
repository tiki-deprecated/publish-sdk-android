package com.mytiki.tiki_sdk_android

import android.content.Context
import com.mytiki.tiki_sdk_flutter_plugin.TikiSdkConsent
import com.mytiki.tiki_sdk_flutter_plugin.TikiSdkDestination
import com.mytiki.tiki_sdk_flutter_plugin.TikiSdkPlugin

class TikiSdk(
    origin: String,
    apiKey: String,
    context: Context?,
    private var flutterPlugin: TikiSdkPlugin = TikiSdkPlugin(origin, apiKey, context),
) {

    suspend fun assignOwnership(
        source: String,
        type: String,
        contains: List<String>,
        origin: String? = null
    ): String {
        return flutterPlugin.caller!!.assignOwnership(
            source, type, contains, origin
        )
    }

    suspend fun modifyConsent(
        source: String,
        destination: TikiSdkDestination,
        about: String? = null,
        reward: String? = null
    ): String {
        return flutterPlugin.caller!!.modifyConsent(source, destination, about, reward)
    }

    suspend fun getConsent(
        source: String,
        origin: String? = null
    ): TikiSdkConsent {
        return flutterPlugin.caller!!.getConsent(source, origin)
    }

    suspend fun applyConsent(
        source: String,
        destination: TikiSdkDestination,
        request: () -> Unit,
        onBlock: (value: String) -> Unit
    ) {
        flutterPlugin.caller!!.applyConsent(source, destination, request, onBlock)
    }

}