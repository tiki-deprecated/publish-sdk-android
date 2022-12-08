package com.mytiki.tiki_sdk_android

import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import java.util.*

/**
 * Tiki sdk plugin
 *
 * @property apiKey
 * @property origin
 * @constructor
 *
 * @param context
 */

class TikiSdkFlutterChannel : FlutterPlugin, MethodCallHandler  {
    private lateinit var channel: MethodChannel
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
        println("method: ${call.method} response: ${response ?: ""}")
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

    fun invokeMethod(method: String, arguments: MutableMap<String,Any?>, callback: ((Boolean, String) -> Unit)) {
        val requestId = UUID.randomUUID().toString()
        callbacks[requestId] = callback
        arguments["requestId"] = requestId
        channel.invokeMethod(method, arguments)
    }
}
