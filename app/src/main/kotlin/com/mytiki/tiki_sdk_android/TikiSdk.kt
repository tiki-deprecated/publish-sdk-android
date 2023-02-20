/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android

import android.content.Context
import com.mytiki.tiki_sdk_android.tiki_platform_channel.MethodEnum
import com.mytiki.tiki_sdk_android.tiki_platform_channel.TikiPlatformChannel
import com.mytiki.tiki_sdk_android.tiki_platform_channel.req.*
import com.mytiki.tiki_sdk_android.tiki_platform_channel.rsp.RspBuild
import com.mytiki.tiki_sdk_android.tiki_platform_channel.rsp.RspConsentApply
import com.mytiki.tiki_sdk_android.tiki_platform_channel.rsp.RspConsentGet
import com.mytiki.tiki_sdk_android.tiki_platform_channel.rsp.RspOwnership
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.embedding.engine.loader.FlutterLoader
import io.flutter.plugins.GeneratedPluginRegistrant
import kotlinx.coroutines.*
import java.util.*

/**
 * The TIKI SDK main class. Use this to add tokenized data ownership, consent, and rewards.
 *
 * @constructor Create empty Tiki sdk. [init] should be called to build the SDK.
  */
class TikiSdk {
    private lateinit var tikiPlatformChannel: TikiPlatformChannel
    lateinit var address: String

    /**
     * Initializes the TIKI SDK.
     *
     * It should be called before any other method. It sets up Flutter Engine and Platform Channel
     * and builds the core of the TIKI SDK, calling TIKI SDK Dart through the Flutter Platform Channel.
     *
     * @param publishingId The publishingId for connecting to TIKI cloud.
     * @param origin The default origin for all transactions.
     * @param context The context of the application. Used to initialize Flutter Engine
     * @param address The address of the user node in TIKI blockchain. If null a new address will be created.
     * @return Deferred<[TikiSdk]> - use await() to complete it.
     */
    fun init(
        publishingId: String,
        origin: String,
        context: Context,
        address: String? = null
    ): Deferred<TikiSdk> {
        return MainScope().async {
            val loader = FlutterLoader()
            loader.startInitialization(context)
            yield()
            loader.ensureInitializationComplete(context, null)
            val flutterEngine = FlutterEngine(context)
            flutterEngine.dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
            GeneratedPluginRegistrant.registerWith(flutterEngine)
            val tikiPlatformChannel = TikiPlatformChannel()
            this@TikiSdk.tikiPlatformChannel = tikiPlatformChannel
            flutterEngine.plugins.add(tikiPlatformChannel)
            yield()
            val rspBuild = tikiPlatformChannel
                .invokeMethod<RspBuild, ReqBuild>(
                    MethodEnum.BUILD,
                    ReqBuild(publishingId, origin, address)
                )
                .await()
            this@TikiSdk.address = rspBuild!!.address
            return@async this@TikiSdk
        }
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
    suspend fun assignOwnership(
        source: String,
        type: TikiSdkDataTypeEnum,
        contains: List<String>,
        about: String? = null,
        origin: String? = null
    ): String {
        val assignReq = ReqOwnershipAssign(source, type, contains, about, origin)
        val rspAssignCompletable: CompletableDeferred<RspOwnership?> =
            tikiPlatformChannel.invokeMethod(
                MethodEnum.ASSIGN_OWNERSHIP, assignReq
            )
        val rspAssign: RspOwnership = rspAssignCompletable.await()!!
        return rspAssign.ownership!!.transactionId
    }

    /**
     * Get ownership
     *
     * @param source String The source of the data (reversed FQDN of the company or product)
     * @param origin String? Optionally overrides the default origin
     *
     * @return [TikiSdkOwnership], null if not found
     */
    suspend fun getOwnership(
        source: String,
        origin: String? = null
    ): TikiSdkOwnership? {
        val getReq = ReqConsentGet(source, origin)
        val rspGetCompletable: CompletableDeferred<RspOwnership?> =
            tikiPlatformChannel.invokeMethod(
                MethodEnum.GET_OWNERSHIP, getReq
            )
        val rspGet = rspGetCompletable.await()
        return rspGet?.ownership
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
     * @param destination [TikiSdkDestination] The destination to which the consent is given.
     * @param about String? Optional description about the data.
     * @param reward String? Optional reward the user will receive for granting consent.
     * @param expiry Date? Optional expiration for the consent.
     * @return [TikiSdkConsent]
     */
    suspend fun modifyConsent(
        ownershipId: String,
        destination: TikiSdkDestination,
        about: String? = null,
        reward: String? = null,
        expiry: Date? = null
    ): TikiSdkConsent {
        val modifyReq = ReqConsentModify(ownershipId, destination, about, reward, expiry?.time)
        val rspModifyCompletable: CompletableDeferred<RspConsentGet?> =
            tikiPlatformChannel.invokeMethod(
                MethodEnum.MODIFY_CONSENT, modifyReq
            )
        val rspModify: RspConsentGet = rspModifyCompletable.await()!!
        return rspModify.consent!!
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
     * @return [TikiSdkConsent]
     */
    suspend fun getConsent(
        source: String,
        origin: String? = null
    ): TikiSdkConsent? {
        val getReq = ReqConsentGet(source, origin)
        val rspConsentCompletable: CompletableDeferred<RspConsentGet?> =
            tikiPlatformChannel.invokeMethod(
                MethodEnum.GET_CONSENT, getReq
            )
        val rspGet: RspConsentGet? = rspConsentCompletable.await()
        return rspGet?.consent
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
    suspend fun applyConsent(
        source: String,
        destination: TikiSdkDestination,
        request: () -> Unit,
        onBlocked: ((value: String) -> Unit)? = null,
        origin: String? = null,
    ) {
        val applyReq = ReqConsentApply(source, destination, origin)
        val rspApplyCompletable: CompletableDeferred<RspConsentApply?> =
            tikiPlatformChannel.invokeMethod(
                MethodEnum.APPLY_CONSENT, applyReq
            )
        val rspApply: RspConsentApply = rspApplyCompletable.await()!!
        if (rspApply.success) {
            request()
        } else {
            onBlocked?.invoke(rspApply.reason ?: "no consent found")
        }
    }
}