package com.mytiki.tiki_sdk_android

import android.content.Context
import androidx.annotation.NonNull
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

/**
 * Tiki sdk plugin
 *
 * @property apiKey
 * @property origin
 * @constructor
 *
 * @param context
 */
class TikiSdkFlutterChannel(
    private val apiKey: String,
    private val origin: String,
    private val tikiSdk: TikiSdk,
    context: Context? = null
) : FlutterPlugin, MethodChannel.MethodCallHandler  {

    private var flutterEngine: FlutterEngine? = null
    var methodChannel: MethodChannel? = null

    companion object {
        const val channelId = "tiki_sdk_flutter"
    }

    init {
        if (context != null) {
            setupChannel(context)
        }
    }

    private fun setupChannel(context: Context) {
        if (methodChannel == null) {
            if (flutterEngine == null) flutterEngine = FlutterEngine(context)
            methodChannel = MethodChannel(flutterEngine!!.dartExecutor, channelId)
        }
        buildSdk()
    }

    private fun setupChannel(messenger: BinaryMessenger) {
        methodChannel = MethodChannel(messenger, channelId)
        buildSdk()
    }

    private fun buildSdk() {
        methodChannel!!.setMethodCallHandler(this)
        methodChannel!!.invokeMethod(
            "build", mapOf(
                "apiKey" to apiKey,
                "origin" to origin
            )
        )
    }

    private fun teardownChannel() {
        methodChannel!!.setMethodCallHandler(null)
        methodChannel = null
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: MethodChannel.Result) {
        val requestId = call.argument<String>("requestId")
        val response = call.argument<String?>("response")
        if (requestId == null) result.error("-1", "missing requestId argument", call.arguments)
        when (call.method) {
            "success" -> {
                response?.let { tikiSdk.completables[requestId]?.complete(it) }
            }
            "error" -> {
                tikiSdk.completables[requestId]?.completeExceptionally(Exception(response))
            }
            else -> result.notImplemented()
        }
        tikiSdk.completables.remove(requestId)
    }

    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        setupChannel(binding.binaryMessenger)
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        teardownChannel()
    }

}
