package com.mytiki.tiki_sdk_android

import android.content.Context
import java.util.*


class TikiSdk(
    apiKey: String,
    origin: String,
    context: Context
) {
    var callbacks: MutableMap<String, (Boolean, String) -> Unit> = mutableMapOf()
    var tikiSdkFlutterChannel: TikiSdkFlutterChannel

    init {
        tikiSdkFlutterChannel = TikiSdkFlutterChannel(
            apiKey, origin, this, context
        )
    }

    /** Assign ownership to a given source
     *
     * Assign ownership to a given [source]. : .
     * [type] . Optionally, the [origin] can be overridden
     * for the specific ownership grant.
     *
     * @param source String The source of the data (reversed FQDN of the company or product)
     * @param type String The type of data: point, pool, or stream
     * @param contains List<String> The list data assets (email, phone, images, etc)
     * @param origin String? Optionally overrides the default origin
     *
     * @return String base64 from blockchain transaction id for ownership
     */
    fun assignOwnership(
        source: String,
        type: String,
        contains: List<String>,
        callback: ((String?) -> Unit)? = null,
        origin: String? = null
    ) {
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
        callback?.let {
            callbacks[requestId] = { result, response ->
                if (result) callback(response)
            }
        }
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
     fun modifyConsent(
        source: String,
        destination: TikiSdkDestination,
        about: String? = null,
        reward: String? = null,
        callback: ((String) -> Unit)? = null
       ) {
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
        callback?.let {
            callbacks[requestId] = { result, response ->
                if (result) callback(response)
            }
        }
    }

    fun getConsent(
        source: String,
        callback: ((TikiSdkConsent) -> Unit)? = null,
        origin: String? = null
    ){
        val requestId = UUID.randomUUID().toString()
        tikiSdkFlutterChannel.methodChannel!!.invokeMethod(
            "getConsent", mapOf(
                "requestId" to requestId,
                "source" to source,
                "origin" to origin,
            )
        )
        callback?.let {
            callbacks[requestId] = { result, response ->
                if (result) callback(TikiSdkConsent.fromJson(response))
            }
        }
    }

    fun applyConsent(
        source: String,
        destination: TikiSdkDestination,
        request: (String) -> Unit,
        onBlocked: (String) -> Unit
    ) {
        val requestId = UUID.randomUUID().toString()
        tikiSdkFlutterChannel.methodChannel!!.invokeMethod(
            "applyConsent", mapOf(
                "requestId" to requestId,
                "source" to source,
                "destination" to destination.toJson(),
            )
        )
        callbacks[requestId] = { result, response ->
            if(result){
                request(response)
            }else{
                onBlocked("no consent")
            }
        }
    }
}