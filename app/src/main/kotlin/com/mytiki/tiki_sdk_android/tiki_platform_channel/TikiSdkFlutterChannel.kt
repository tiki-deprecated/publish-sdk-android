/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.tiki_platform_channel

import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/**
 * Tiki sdk flutter channel
 *
 * @constructor Create empty Tiki sdk flutter channel
 */
class TikiSdkFlutterChannel : FlutterPlugin, MethodCallHandler {
    lateinit var channel: MethodChannel
    private var callbacks: MutableMap<String, (Boolean, String) -> Unit> = mutableMapOf()

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "tiki_sdk_flutter")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        val requestId: String? = call.argument<String>("requestId")
        val response: String? = call.argument<String?>("response")
        if (requestId == null) result.error("-1", "missing requestId argument", call.arguments)
        val callback = callbacks[requestId]
        when (call.method) {
            "success" -> {
                if (callback != null) callback(true, response ?: "")
            }
            "error" -> {
                if (callback != null) callback(false, response ?: "")
            }
            else -> result.notImplemented()
        }
        callbacks.remove(requestId)
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
}
