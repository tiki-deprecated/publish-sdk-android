package com.mytiki.tiki_sdk_android

import android.content.Context
import com.mytiki.tiki_sdk_android.tiki_platform_channel.TikiPlatformChannel
import com.mytiki.tiki_sdk_android.tiki_platform_channel.MethodEnum
import com.mytiki.tiki_sdk_android.tiki_platform_channel.req.*
import com.mytiki.tiki_sdk_android.tiki_platform_channel.rsp.RspBuild
import com.mytiki.tiki_sdk_android.tiki_platform_channel.rsp.RspConsentApply
import com.mytiki.tiki_sdk_android.tiki_platform_channel.rsp.RspConsentGet
import com.mytiki.tiki_sdk_android.tiki_platform_channel.rsp.RspOwnership
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.embedding.engine.loader.FlutterLoader
import io.flutter.plugins.GeneratedPluginRegistrant
import kotlinx.coroutines.CompletableDeferred
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
class TikiSdk {

    private lateinit var tikiSdkFlutterChannel: TikiPlatformChannel
    lateinit var address: String

    fun init(apiId: String, origin: String, context: Context, address: String? = null) : CompletableDeferred<TikiSdk> {
        val response = CompletableDeferred<TikiSdk>()
        android.os.Handler(context.mainLooper).post {
            val loader = FlutterLoader()
            loader.startInitialization(context)
            loader.ensureInitializationComplete(context, null)
            val flutterEngine = FlutterEngine(context)
            flutterEngine.dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
            GeneratedPluginRegistrant.registerWith(flutterEngine)
            tikiSdkFlutterChannel = TikiPlatformChannel()
            flutterEngine.plugins.add(tikiSdkFlutterChannel)
            val method = MethodEnum.BUILD
            val buildRequest = ReqBuild(apiId, origin, address)
            val rspBuildCompletable: CompletableDeferred<RspBuild?> =
                tikiSdkFlutterChannel.invokeMethod(method, buildRequest)
            runBlocking {
                val rspBuild = rspBuildCompletable.await()
                this@TikiSdk.address = rspBuild!!.address
                response.complete(this@TikiSdk)
            }
        }
        return response
    }
//
//    /** Assign ownership to a given source.
//     *
//     * @param source String The source of the data (reversed FQDN of the company or product)
//     * @param type String The type of data: point, pool, or stream
//     * @param contains List<String> The list data assets (email, phone, images, etc)
//     * @param about String? Optional description about the data.
//     * @param origin String? Optionally overrides the default origin
//     *
//     * @return [TikiSdkOwnership] transaction Id
//     */
//    suspend fun assignOwnership(
//        source: String,
//        type: TikiSdkDataTypeEnum,
//        contains: List<String>,
//        about: String? = null,
//        origin: String? = null
//    ): String {
//        val assignReq = ReqOwnershipAssign(source, type, contains, about, origin)
//        val rspAssign: RspOwnership = tikiSdkFlutterChannel.invokeMethod(
//            MethodEnum.ASSIGN_OWNERSHIP, assignReq
//        )!!
//        return rspAssign.ownership.transactionId
//    }
//
//    /**
//     * Get ownership
//     *
//     * @param source String The source of the data (reversed FQDN of the company or product)
//     * @param origin String? Optionally overrides the default origin
//     *
//     * @return [TikiSdkOwnership], null if not found
//     */
//    suspend fun getOwnership(
//        source: String,
//        origin: String? = null
//    ): TikiSdkOwnership? {
//        val getReq = ReqConsentGet(source, origin)
//        val rspGet: RspOwnership? = tikiSdkFlutterChannel.invokeMethod(
//            MethodEnum.GET_OWNERSHIP, getReq
//        )
//        return rspGet?.ownership
//    }
//
//    /**
//     * Modify consent for an ownership identified by [ownershipId].
//     *
//     *  The Ownership must be granted before modifying consent. Consent is applied
//     *  on an explicit only basis. Meaning all requests will be denied by default
//     *  unless the destination is explicitly defined in *destination*.
//     *  A blank list of [TikiSdkDestination.uses] or [TikiSdkDestination.paths]
//     *  means revoked consent.
//     *
//     * @param ownershipId String The transaction id of the ownership registry.
//     * @param destination TikiSdkDestination
//     * @param about String? Optional description about the data.
//     * @param reward String? Optional reward the user will receive for granting consent.
//     * @param expiry Date? Optional expiration for the consent.
//     * @return
//     */
//    suspend fun modifyConsent(
//        ownershipId: String,
//        destination: TikiSdkDestination,
//        about: String? = null,
//        reward: String? = null,
//        expiry: Date? = null
//    ): TikiSdkConsent {
//        val modifyReq = ReqConsentModify(ownershipId, destination, about, reward, expiry?.time)
//        val rspModify: RspConsentGet = tikiSdkFlutterChannel.invokeMethod(
//            MethodEnum.MODIFY_CONSENT, modifyReq
//        )!!
//        return rspModify.consent
//    }
//
//    /**
//     * Gets latest consent given for a *source* and *origin*.
//     *
//     * It does not validate if the consent is expired or if it can be applied to
//     * a specific destination. For that, [applyConsent] should be used instead.
//     * If no [origin] is specified, it uses the default origin.
//     *
//     * @param source String
//     * @param origin String?
//     *
//     * @return TikiSdkConsent
//     */
//    suspend fun getConsent(
//        source: String,
//        origin: String? = null
//    ): TikiSdkConsent? {
//        val getReq = ReqConsentGet(source, origin)
//        val rspGet: RspConsentGet? = tikiSdkFlutterChannel.invokeMethod(
//            MethodEnum.GET_CONSENT, getReq
//        )
//        return rspGet?.consent
//    }
//
//    /**
//     *  Apply consent for a given [source] and [destination].
//     *
//     *  If consent exists for the destination and is not expired, [request] will be
//     *  executed. Else [onBlocked] is called.
//     *
//     * @param source String
//     * @param destination TikiSdkDestination
//     * @param origin String?
//     * @param request () -> Unit
//     * @param onBlocked ((String) -> Unit)?
//     */
//    suspend fun applyConsent(
//        source: String,
//        destination: TikiSdkDestination,
//        request: () -> Unit,
//        onBlocked: ((value: String) -> Unit)? = null,
//        origin: String? = null,
//    ) {
//        val applyReq = ReqConsentApply(source, destination, origin)
//        val rspApply: RspConsentApply? = tikiSdkFlutterChannel.invokeMethod(
//            MethodEnum.APPLY_CONSENT, applyReq
//        )
//        if (rspApply!!.success) {
//            request()
//        } else {
//            onBlocked?.invoke(rspApply.reason ?: "no consent found")
//        }
//    }
}