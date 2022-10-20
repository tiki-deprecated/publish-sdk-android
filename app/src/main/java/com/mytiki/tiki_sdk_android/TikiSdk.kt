package com.mytiki.tiki_sdk_android

import android.content.Context
import com.mytiki.tiki_sdk_flutter_plugin.TikiSdkConsent
import com.mytiki.tiki_sdk_flutter_plugin.TikiSdkDestination
import com.mytiki.tiki_sdk_flutter_plugin.TikiSdkPlugin

class TikiSdk(
    origin: String,
    apiKey: String,
    context: Context?,
    private var flutterPlugin: TikiSdkPlugin = TikiSdkPlugin(origin, apiKey, context),
) {

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
        return flutterPlugin.caller!!.assignOwnership(
            source, type, contains, origin
        )
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
    ): String {
        return flutterPlugin.caller!!.modifyConsent(source, destination, about, reward)
    }

    /**
     * Get consent
     *
     * Rtrieves the latest Consent given for [source] and [origin]
     *
     * @param source String The source of the given consent
     * @param origin String? Optionally overrides the default [TikiSdkPlugin.origin]
     * @return TikiSdkConsent The consent object.
     */
    suspend fun getConsent(
        source: String,
        origin: String? = null
    ): TikiSdkConsent {
        return flutterPlugin.caller!!.getConsent(source, origin)
    }

    /**
     * Apply consent
     *
     * Apply consent for a data asset identified by its [source] and [destination].
     * If consent exists for the destination, [request] will be executed. Else [onBlock] is called.
     *
     * @param source String The source of the given consent
     * @param destination TikiSdkDestination
     * @param request (value:String) -> Unit Function to be called if consent was given
     * @param onBlock (value:String) -> Unit Function to be called if consent does not exist
     * @receiver
     * @receiver
     */
    suspend fun applyConsent(
        source: String,
        destination: TikiSdkDestination,
        request: (value:String) -> Unit,
        onBlock: (value: String) -> Unit
    ) {
        flutterPlugin.caller!!.applyConsent(source, destination, request, onBlock)
    }

}