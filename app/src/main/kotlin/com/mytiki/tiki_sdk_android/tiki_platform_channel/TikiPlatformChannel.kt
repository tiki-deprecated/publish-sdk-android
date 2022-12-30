/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.tiki_platform_channel

import android.util.Log
import androidx.annotation.NonNull
import com.mytiki.tiki_sdk_android.tiki_platform_channel.rsp.RspError
import com.mytiki.tiki_sdk_android.util.TimeStampToDateAdapter
import com.squareup.moshi.Moshi
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import kotlinx.coroutines.CompletableDeferred
import okio.IOException
import java.util.*

/**
 * Tiki sdk flutter channel
 *
 * @constructor Create empty Tiki sdk flutter channel
 */
class TikiPlatformChannel : FlutterPlugin, MethodCallHandler {

    lateinit var channel: MethodChannel
    var completables: MutableMap<String, ((String?, Error?) -> Unit)> = mutableMapOf()

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "tiki_sdk_flutter")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        val response = call.argument<String>("response")!!
        val requestId = call.argument<String>("requestId")!!
        Log.e("Ricardo - call", call.method)
        Log.e("Ricardo - reqId", requestId)
        Log.e("Ricardo - rsp", response)
        when (call.method) {
            "success" -> {
                completables[requestId]?.invoke(response, null)
            }
            "error" -> {
                val error = Moshi.Builder().build().adapter(RspError::class.java).fromJson(response)
                completables[requestId]?.invoke(null, Error(error?.message))
            }
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    /**
     * Invoke [method] in TIKI SDK Flutter
     *
     * @param T The type that will be returned in the CompletableDeferred
     * @param R The type of the [request]
     * @param method The method to be called from TIKI SDK Flutter
     * @param request The request object for the [method]
     * @return CompletableDeferred holding [T]
     */
    inline fun <reified T, reified R> invokeMethod(
        method: MethodEnum,
        request: R
    ): CompletableDeferred<T?> {
        val moshi: Moshi = Moshi.Builder()
            .add(TimeStampToDateAdapter())
            .build()
        val requestId = UUID.randomUUID().toString()
        val deferred = CompletableDeferred<T?>()
        val jsonRequest = moshi.adapter(R::class.java).toJson(request)
        channel.invokeMethod(
            method.methodCall, mapOf(
                "requestId" to requestId,
                "request" to jsonRequest
            )
        )
        completables[requestId] = { jsonString: String?, error: Error? ->
            if (error != null) {
                deferred.completeExceptionally(error)
            } else {
                try {
                    val response = moshi.adapter(T::class.java).fromJson(jsonString ?: "")
                    deferred.complete(response)
                } catch (e: IOException) {
                    deferred.completeExceptionally(e)
                }
            }
        }
        return deferred
    }
}
