package com.mytiki.tiki_sdk_android

import android.content.Context
import kotlinx.coroutines.CompletableDeferred
import java.util.*


class TikiSdk(
    apiKey: String,
    origin: String,
    context: Context
) {

    var completables: MutableMap<String, CompletableDeferred<String?>> = mutableMapOf()
    var tikiSdkFlutterChannel: TikiSdkFlutterChannel

    init {
        tikiSdkFlutterChannel = TikiSdkFlutterChannel(
            apiKey, origin, this, context
        )
    }

    /** Assign ownership to a given source
     *
     * Assign ownership to a given [source]. : .
     * [types] . Optionally, the [origin] can be overridden
     * for the specific ownership grant.
     *
     * @param source String The source of the data (reversed FQDN of the company or product)
     * @param type String The type of data: point, pool, or stream
     * @param contains List<String> The list data assets (email, phone, images, etc)
     * @param origin String? Optionally overrides the default [TikiSdkPlugin.origin]
     *
     * @return String base64 from blockchain transaction id for ownership
     */
    suspend fun assignOwnership(
        source: String,
        type: String,
        contains: List<String>,
        origin: String? = null
    ): String {
        val requestId = UUID.randomUUID().toString()
        tikiSdkFlutterChannel.methodChannel!!.invokeMethod(
            "assignOwnership", mapOf(
                "requestId" to requestId,
                "source" to source,
                "type" to type,
                "contains" to contains,
                "origin" to origin,
            )
        )
        val deferred = CompletableDeferred<String?>()
        completables[requestId] = deferred
        return deferred.await()!!
    }

    /**
     * Modify consent for using the [source] in [destination].
     *
     * This method overrides any consent that was given before. Ownership must be granted before
     * modifying consent.
     * Consent is applied on an explicit only basis. Meaning all requests will be denied by default
     * unless the destination is explicitly defined in [destination].
     * A blank list of [TikiSdkDestination.uses] or [TikiSdkDestination.paths] means revoked consent.
     *
     * @param source String
     * @param destination TikiSdkDestination
     * @param about String?
     * @param reward String?
     *
     * @return String base64 from blockchain transaction id for consent
     * */
    suspend fun modifyConsent(
        source: String,
        destination: TikiSdkDestination,
        about: String? = null,
        reward: String? = null
    ): String? {
        val requestId = UUID.randomUUID().toString()
        tikiSdkFlutterChannel.methodChannel!!.invokeMethod(
            "modifyConsent", mapOf(
                "requestId" to requestId,
                "source" to source,
                "destination" to destination.toJson(),
                "about" to about,
                "reward" to reward,
            )
        )
        val deferred = CompletableDeferred<String?>()
        completables[requestId] = deferred
        return deferred.await()
    }


    suspend fun getConsent(
        source: String,
        origin: String? = null
    ): TikiSdkConsent? {
        val requestId = UUID.randomUUID().toString()
        tikiSdkFlutterChannel.methodChannel!!.invokeMethod(
            "getConsent", mapOf(
                "requestId" to requestId,
                "source" to source,
                "origin" to origin,
            )
        )
        val deferred = CompletableDeferred<String?>()
        completables[requestId] = deferred
        val jsonConsent = deferred.await()
        return jsonConsent?.let { TikiSdkConsent.fromJson(it) }
    }

    suspend fun applyConsent(
        source: String,
        destination: TikiSdkDestination,
        request: (value: String) -> Unit,
        onBlocked: (value: String) -> Unit
    ) {
        val requestId = UUID.randomUUID().toString()
        tikiSdkFlutterChannel.methodChannel!!.invokeMethod(
            "applyConsent", mapOf(
                "requestId" to requestId,
                "source" to source,
                "destination" to destination.toJson(),
            )
        )
        try {
            val deferred = CompletableDeferred<String?>()
            completables[requestId] = deferred
            val value = deferred.await()
            if(value != null){
                request(value)
            }else{
                onBlocked("no consent")
            }
        } catch (e: Exception) {
            onBlocked(e.message ?: "no consent")
        }
    }
}