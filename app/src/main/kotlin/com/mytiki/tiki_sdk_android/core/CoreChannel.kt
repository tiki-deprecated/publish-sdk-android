/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.core

import android.content.Context
import androidx.annotation.NonNull
import com.mytiki.tiki_sdk_android.core.rsp.RspError
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.embedding.engine.loader.FlutterLoader
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugins.GeneratedPluginRegistrant
import kotlinx.coroutines.CompletableDeferred
import java.util.*

/**
 * Tiki sdk flutter channel
 *
 * @constructor Create empty Tiki sdk flutter channel
 */
class CoreChannel(context: Context) : FlutterPlugin, MethodCallHandler {

    private lateinit var channel: MethodChannel
    private var completables: MutableMap<String, ((String, Error?) -> Unit)> = mutableMapOf()

    init {
        val loader = FlutterLoader()
        loader.startInitialization(context)
        loader.ensureInitializationComplete(context, null)
        val flutterEngine = FlutterEngine(context)
        flutterEngine.dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
        flutterEngine.plugins.add(this)
        GeneratedPluginRegistrant.registerWith(flutterEngine)
    }

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "tiki_sdk_flutter")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        val response = call.argument<String>("response")!!
        val requestId = call.argument<String>("requestId")!!
        when (call.method) {
            "success" -> {
                completables[requestId]?.invoke(response, null)
            }
            "error" -> {
                val error = RspError.fromJson(response)
                completables[requestId]?.invoke("", Error(error.message))
            }
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    /**
     * Invoke [method] in TIKI SDK Flutter
     *
     * @param method The method to be called from TIKI SDK Flutter
     * @param jsonRequest: The request JSON object for the [method]
     * @return CompletableDeferred holding JSON [String]
     */
    fun invokeMethod(
        method: CoreMethod,
        jsonRequest: String
    ): CompletableDeferred<String> {
        val requestId = UUID.randomUUID().toString()
        val deferred = CompletableDeferred<String>()
        channel.invokeMethod(
            method.value, mapOf(
                "requestId" to requestId,
                "request" to jsonRequest
            )
        )
        completables[requestId] = { jsonString: String, error: Error? ->
            if (error != null) {
                deferred.completeExceptionally(error)
            } else {
                deferred.complete(jsonString)
            }
        }
        return deferred
    }
}
