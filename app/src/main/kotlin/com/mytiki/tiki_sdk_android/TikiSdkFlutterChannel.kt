package com.mytiki.tiki_sdk_android

import android.content.Context
import androidx.annotation.NonNull
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.dart.DartExecutor.DartEntrypoint
import io.flutter.embedding.engine.loader.FlutterLoader
import io.flutter.embedding.engine.plugins.util.GeneratedPluginRegister
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;

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
) : MethodChannel.MethodCallHandler  {

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
            val loader = FlutterLoader()
            loader.startInitialization(context)
            loader.ensureInitializationComplete(context, null)
            val flutterEngine = FlutterEngine(context)
            flutterEngine.dartExecutor.executeDartEntrypoint(
                DartEntrypoint.createDefault()
            )
            GeneratedPluginRegister.registerGeneratedPlugins(flutterEngine)
            methodChannel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, channelId)
        }
        buildSdk()
    }

    private fun buildSdk() {
        methodChannel!!.setMethodCallHandler(this)
        methodChannel!!.invokeMethod(
            "build", mapOf(
                "apiKey" to apiKey,
                "origin" to origin,
                "requestId" to "build"
            )
        )
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: MethodChannel.Result) {
        val requestId = call.argument<String>("requestId")
        val response = call.argument<String?>("response")
        if (requestId == null) result.error("-1", "missing requestId argument", call.arguments)
        val callback = tikiSdk.callbacks[requestId]
        when (call.method) {
            "success" -> {
                if (callback != null) {
                    val valueToReturn: String = response.let{ "" }
                    callback(true, valueToReturn)
                }
            }
            "error" -> {
                if (callback != null) {
                    val valueToReturn: String = response.let{ "" }
                    callback(false, valueToReturn)
                }
            }
            else -> result.notImplemented()
        }
        tikiSdk.callbacks.remove(requestId)
    }

}
