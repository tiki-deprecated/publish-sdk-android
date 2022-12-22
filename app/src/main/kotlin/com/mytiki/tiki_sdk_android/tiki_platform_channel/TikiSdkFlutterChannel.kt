/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.tiki_platform_channel

import androidx.annotation.NonNull
import com.mytiki.tiki_sdk_android.TikiSdk
import com.mytiki.tiki_sdk_android.tiki_platform_channel.rsp.RspError
import com.squareup.moshi.Moshi
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import org.json.JSONObject

/**
 * Tiki sdk flutter channel
 *
 * @constructor Create empty Tiki sdk flutter channel
 */
class TikiSdkFlutterChannel : FlutterPlugin, MethodCallHandler {
    lateinit var tikiSdk: TikiSdk
    lateinit var channel: MethodChannel

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "tiki_sdk_flutter")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        val response = call.argument<String>("response")!!
        val jsonMap = JSONObject(response)
        val requestId = jsonMap["requestId"]
        when (call.method) {
            "success" -> {
                tikiSdk.completables[requestId]?.complete(response)
            }
            "error" -> {
                val error = Moshi.Builder().build().adapter(RspError::class.java).fromJson(response)
                tikiSdk.completables[requestId]?.completeExceptionally(Error(error?.message))
            }
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
}
