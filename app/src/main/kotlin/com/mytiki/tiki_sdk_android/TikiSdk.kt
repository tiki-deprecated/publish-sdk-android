package com.mytiki.tiki_sdk_android

import android.content.Context
import com.mytiki.tiki_sdk_android.tiki_platform_channel.TikiSdkFlutterChannel
import com.mytiki.tiki_sdk_android.tiki_platform_channel.req.*
import com.mytiki.tiki_sdk_android.tiki_platform_channel.rsp.RspBuild
import com.mytiki.tiki_sdk_android.tiki_platform_channel.rsp.RspConsentApply
import com.mytiki.tiki_sdk_android.tiki_platform_channel.rsp.RspConsentGet
import com.mytiki.tiki_sdk_android.tiki_platform_channel.rsp.RspOwnership
import com.squareup.moshi.Moshi
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.embedding.engine.loader.FlutterLoader
import io.flutter.plugins.GeneratedPluginRegistrant
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.util.*

/**
 * The TIKI SDK main class. Use this to add tokenized data ownership, consent, and rewards.
 *
 * @constructor
 *
 * @param apiId The apiId for connecting to TIKI cloud.
 * @param origin The default origin for all transactions.
 * @param context The context of the application. Used to initialize Flutter Engine
 * @param address The address of the user node in TIKI blockchain. If null a new address will be created.
 */
class TikiSdk(apiId: String, origin: String, context: Context, address: String? = null) {

    private var tikiSdkFlutterChannel: TikiSdkFlutterChannel
    private val moshi = Moshi.Builder().build()
    var completables: MutableMap<String, CompletableDeferred<String?>> = mutableMapOf()
    val address: String

    init {
        val buildRequest = ReqBuild("build", apiId, origin, address)
        val buildRequestJson = buildRequest.toJson()
        var jsonString: String
        val loader = FlutterLoader()
        loader.startInitialization(context)
        loader.ensureInitializationComplete(context, null)
        val flutterEngine = FlutterEngine(context)
        flutterEngine.dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
        GeneratedPluginRegistrant.registerWith(flutterEngine)
        tikiSdkFlutterChannel = TikiSdkFlutterChannel()
        tikiSdkFlutterChannel.tikiSdk = this
        flutterEngine.plugins.add(tikiSdkFlutterChannel)
        tikiSdkFlutterChannel.channel.invokeMethod(
            "build", listOf("request" to buildRequestJson)
        )
        val deferred = CompletableDeferred<String?>()
        completables["build"] = deferred
        runBlocking(Dispatchers.IO) {
            jsonString = deferred.await()!!
        }
        val rspBuild: RspBuild = moshi.adapter(RspBuild::class.java).fromJson(jsonString)!!
        this@TikiSdk.address = rspBuild.address
    }

    /** Assign ownership to a given source.
     *
     * @param source String The source of the data (reversed FQDN of the company or product)
     * @param type String The type of data: point, pool, or stream
     * @param contains List<String> The list data assets (email, phone, images, etc)
     * @param about String? Optional description about the data.
     * @param origin String? Optionally overrides the default origin
     *
     * @return [TikiSdkOwnership] transaction Id
     */
    fun assignOwnership(
        source: String,
        type: TikiSdkDataTypeEnum,
        contains: List<String>,
        about: String? = null,
        origin: String? = null
    ): String {
        val requestId = UUID.randomUUID().toString()
        var jsonString: String
        tikiSdkFlutterChannel.channel.invokeMethod(
            "assignOwnership", mapOf(
                "request" to ReqOwnershipAssign(
                    requestId, source, type, contains, about, origin
                ).toJson()
            )
        )
        val deferred = CompletableDeferred<String?>()
        completables[requestId] = deferred
        runBlocking(Dispatchers.IO) {
            jsonString = deferred.await()!!
        }
        val rsp = moshi.adapter(RspOwnership::class.java).fromJson(jsonString)
        return rsp!!.ownership.transactionId
    }

    /**
     * Get ownership
     *
     * @param source String The source of the data (reversed FQDN of the company or product)
     * @param origin String? Optionally overrides the default origin
     *
     * @return [TikiSdkOwnership], null if not found
     */
    fun getOwnership(
        source: String,
        origin: String? = null
    ): TikiSdkOwnership? {
        val requestId = UUID.randomUUID().toString()
        var jsonString: String
        tikiSdkFlutterChannel.channel.invokeMethod(
            "assignOwnership", mapOf(
                "request" to ReqOwnershipGet(
                    requestId, source, origin
                ).toJson()
            )
        )
        val deferred = CompletableDeferred<String?>()
        completables[requestId] = deferred
        runBlocking(Dispatchers.IO) {
            jsonString = deferred.await()!!
        }
        val rsp = moshi.adapter(RspOwnership::class.java).fromJson(jsonString)
        return rsp?.ownership
    }

    /**
     * Modify consent for an ownership identified by [ownershipId].
     *
     *  The Ownership must be granted before modifying consent. Consent is applied
     *  on an explicit only basis. Meaning all requests will be denied by default
     *  unless the destination is explicitly defined in *destination*.
     *  A blank list of [TikiSdkDestination.uses] or [TikiSdkDestination.paths]
     *  means revoked consent.
     *
     * @param ownershipId String The transaction id of the ownership registry.
     * @param destination TikiSdkDestination
     * @param about String? Optional description about the data.
     * @param reward String? Optional reward the user will receive for granting consent.
     * @param expiry Date? Optional expiration for the consent.
     * @return
     */
    fun modifyConsent(
        ownershipId: String,
        destination: TikiSdkDestination,
        about: String? = null,
        reward: String? = null,
        expiry: Date? = null
    ): TikiSdkConsent {
        val requestId = UUID.randomUUID().toString()
        var jsonString: String
        tikiSdkFlutterChannel.channel.invokeMethod(
            "assignOwnership", mapOf(
                "request" to ReqConsentModify(
                    requestId, ownershipId, destination, about, reward, expiry?.time
                ).toJson()
            )
        )
        val deferred = CompletableDeferred<String?>()
        completables[requestId] = deferred
        runBlocking(Dispatchers.IO) {
            jsonString = deferred.await()!!
        }
        val rsp = moshi.adapter(RspConsentGet::class.java).fromJson(jsonString)
        return rsp!!.consent
    }

    /**
     * Gets latest consent given for a *source* and *origin*.
     *
     * It does not validate if the consent is expired or if it can be applied to
     * a specific destination. For that, [applyConsent] should be used instead.
     * If no [origin] is specified, it uses the default origin.
     *
     * @param source String
     * @param origin String?
     *
     * @return TikiSdkConsent
     */
    fun getConsent(
        source: String,
        origin: String? = null
    ): TikiSdkConsent? {
        val requestId = UUID.randomUUID().toString()
        var jsonString: String
        tikiSdkFlutterChannel.channel.invokeMethod(
            "assignOwnership", mapOf(
                "request" to ReqConsentGet(
                    requestId, source, origin
                ).toJson()
            )
        )
        val deferred = CompletableDeferred<String?>()
        completables[requestId] = deferred
        runBlocking(Dispatchers.IO) {
            jsonString = deferred.await()!!
        }
        val rsp = moshi.adapter(RspConsentGet::class.java).fromJson(jsonString)
        return rsp?.consent
    }

    /**
     *  Apply consent for a given [source] and [destination].
     *
     *  If consent exists for the destination and is not expired, [request] will be
     *  executed. Else [onBlocked] is called.
     *
     * @param source String
     * @param destination TikiSdkDestination
     * @param origin String?
     * @param request () -> Unit
     * @param onBlocked ((String) -> Unit)?
     */
    fun applyConsent(
        source: String,
        destination: TikiSdkDestination,
        request: () -> Unit,
        onBlocked: ((value: String) -> Unit)? = null,
        origin: String? = null,
    ) {
        val requestId = UUID.randomUUID().toString()
        var jsonString: String
        tikiSdkFlutterChannel.channel.invokeMethod(
            "assignOwnership", mapOf(
                "request" to ReqConsentApply(
                    requestId, source, destination, origin
                ).toJson()
            )
        )
        val deferred = CompletableDeferred<String?>()
        completables[requestId] = deferred
        runBlocking(Dispatchers.IO) {
            jsonString = deferred.await()!!
        }
        val rsp = moshi.adapter(RspConsentApply::class.java).fromJson(jsonString)
        if (rsp!!.success) {
            request()
        } else {
            onBlocked?.invoke(rsp.reason ?: "no consent")
        }
    }
}