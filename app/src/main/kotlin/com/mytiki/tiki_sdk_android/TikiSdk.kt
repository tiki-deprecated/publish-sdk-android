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

    /**
     * Assign ownership to a given [source].
     *
     * The [type] identifies which type the ownership refers to.
     * The list of items the data contains is described by [contains]. Optionally,
     * a description about this ownership can be given in [about] and the [origin]
     * can be overridden for the specific ownership grant.
     *
     * @param source String The source of the data (reversed FQDN of the company or product)
     * @param type String The type of data: point, pool, or stream
     * @param contains List<String> The list data assets (email, phone, images, etc)
     * @param callback ((ownershipId: String) -> Unit)? Optional callback to handle the ownershipId
     * @param origin String? Optionally overrides the default origin
     *
     */
    fun assignOwnership(
        source: String,
        type: String,
        callback: ((ownershipId: String) -> Unit)? = null,
        contains: List<String>? = null,
        about: String? = null,
        origin: String? = null
    ) {
        val requestId = UUID.randomUUID().toString()
        tikiSdkFlutterChannel.methodChannel!!.invokeMethod(
            "assignOwnership", mapOf(
                "requestId" to requestId,
                "source" to source,
                "about" to about,
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
     * @param expiry Calendar?
     * @param callback ((consent: [TikiSdkConsent]) -> Unit)? Optional callback to handle the  [TikiSdkConsent] object.

     */
     fun modifyConsent(
        source: String,
        destination: TikiSdkDestination,
        callback: ((TikiSdkConsent) -> Unit)? = null,
        about: String? = null,
        reward: String? = null,
        expiry: Calendar? = null
       ) {
        val requestId = UUID.randomUUID().toString()
        tikiSdkFlutterChannel.methodChannel!!.invokeMethod(
            "modifyConsent", mapOf(
                "requestId" to requestId,
                "source" to source,
                "destination" to destination.toJson(),
                "about" to about,
                "reward" to reward,
                "expiry" to expiry?.let{ it.timeInMillis / 1000 }
            )
        )
        callback?.let {
            callbacks[requestId] = { result, response ->
                if (result) callback(TikiSdkConsent.fromJson(response))
            }
        }
    }

    /**
     * Get consent
     *
     * Gets latest consent given for a [source] and [origin]. It does not validate if the consent is
     * expired or if it can be applied to a specific destination. For that, [applyConsent] should be
     * used instead.
     *
     * @param source String The source of the data (reversed FQDN of the company or product)
     * @param callback ((consent: [TikiSdkConsent]) -> Unit)? Optional callback to handle the  [TikiSdkConsent] object.
     * @param origin String? Optionally overrides the default origin
     */
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

    /**
     * Apply consent for a given [source] and [destination].
     *
     * If consent exists for the destination and is not expired, [request] will be
     * executed. Else [onBlocked] is called.
     *
     * @param source String
     * @param destination TikiSdkDestination
     * @param request (String) -> Unit
     * @param onBlocked (String) -> Unit
     *
     */
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