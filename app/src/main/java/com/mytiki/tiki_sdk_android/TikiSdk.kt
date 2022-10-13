package com.mytiki.tiki_sdk_android

import android.content.Context
import com.mytiki.tiki_sdk_flutter_plugin.TikiSdkDestination
import com.mytiki.tiki_sdk_flutter_plugin.TikiSdkFlutterPlugin
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class TikiSdk(
    context: Context,
    private val apiKey: String?,
    private var flutterPlugin: TikiSdkFlutterPlugin = TikiSdkFlutterPlugin(),
    private var flutterEngine: FlutterEngine = FlutterEngine(context)
) {

    //        A Flutter plugin has a lifecycle. First, a developer must add a FlutterPlugin to an instance
    //        of FlutterEngine. To do this, obtain a PluginRegistry with FlutterEngine.getPlugins(), then
    //        call PluginRegistry.add(FlutterPlugin), passing the instance of the Flutter plugin. During
    //        the call to PluginRegistry.add(FlutterPlugin), the FlutterEngine will invoke
    //        onAttachedToEngine(FlutterPlugin.FlutterPluginBinding) on the given FlutterPlugin.
    //        If the FlutterPlugin is removed from the FlutterEngine via PluginRegistry.remove(Class),
    //        or if the FlutterEngine is destroyed, the FlutterEngine will invoke
    //        onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding) on the given FlutterPlugin.

    fun build() {
        checkFlutterChannel()
        flutterPlugin.channel!!.invokeMethod(
            "buildSdk", mapOf(
                "apiKey" to apiKey
            )
        )
    }

    fun assignOwnership(
        source: String,
        type: String,
        contains: List<String>,
        origin: String? = null
    ) {
        checkFlutterChannel()
        flutterPlugin.channel!!.invokeMethod(
            "assignOwnership", mapOf(
                "source" to source,
                "type" to type,
                "contains" to contains,
                "origin" to origin,
            )
        )
    }

    fun getConsent(source: String, origin: String?) {
        checkFlutterChannel()
        flutterPlugin.channel!!.invokeMethod(
            "getConsent", mapOf(
                "source" to source,
                "origin" to origin
            )
        )
    }

    fun modifyConsent(
        ownershipId: String, destination: TikiSdkDestination, about: String?, reward: String?
    ) {
        checkFlutterChannel()
        flutterPlugin.channel!!.invokeMethod(
            "modifyConsent", mapOf(
                "ownershipId" to ownershipId,
                "destination" to destination.toJson(),
                "about" to about,
                "reward" to reward,
            )
        )
    }

    fun applyConsent(
        source: String,
        destination: TikiSdkDestination,
        requestId: String,
        request: () -> Unit,
        onBlock: (value: String) -> Unit
    ) {
        checkFlutterChannel()
        flutterPlugin.requestCallbacks[requestId] = request
        flutterPlugin.blockCallbacks[requestId] = onBlock
        flutterPlugin.channel!!.invokeMethod(
            "applyConsent", mapOf(
                "source" to source,
                "destination" to destination,
                "requestId" to requestId,
            )
        )
    }

    private fun checkFlutterChannel() {
        if (flutterPlugin.channel == null) {
            flutterPlugin.channel =
                MethodChannel(flutterEngine.dartExecutor, "com.example.package.background")
            flutterPlugin.channel!!.setMethodCallHandler(flutterPlugin)
        }
    }
}