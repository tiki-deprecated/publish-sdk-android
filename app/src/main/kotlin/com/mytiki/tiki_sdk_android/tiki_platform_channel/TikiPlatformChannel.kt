/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.tiki_platform_channel

import androidx.annotation.NonNull
import com.mytiki.tiki_sdk_android.tiki_platform_channel.rsp.RspError
import com.squareup.moshi.Moshi
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.util.*

/**
 * Tiki sdk flutter channel
 *
 * @constructor Create empty Tiki sdk flutter channel
 */
class TikiPlatformChannel : FlutterPlugin, MethodCallHandler {

    lateinit var channel: MethodChannel
    var completables: MutableMap<String, CompletableDeferred<String?>> = mutableMapOf()

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
                completables[requestId]?.complete(response)
            }
            "error" -> {
                val error = Moshi.Builder().build().adapter(RspError::class.java).fromJson(response)
                completables[requestId]?.completeExceptionally(Error(error?.message))
            }
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    inline fun <reified T, reified R> invokeMethod(
        method: TikiPlatformChannelMethodEnum,
        request: R
    ): T? {
        var jsonString: String
        val requestId = UUID.randomUUID().toString()
        val deferred = CompletableDeferred<String?>()
        val jsonRequest = Moshi.Builder().build().adapter(R::class.java).toJson(request)
        channel.invokeMethod(
            method.methodCall, mapOf(
                "requestId" to requestId,
                "request" to jsonRequest
            )
        )
        completables[requestId] = deferred
        runBlocking(Dispatchers.IO) {
            jsonString = deferred.await()!!
        }
        return Moshi.Builder().build().adapter(T::class.java).fromJson(jsonString)
    }
}
