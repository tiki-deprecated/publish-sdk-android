/**
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android

import CoreMethod
import TitleRecord
import android.content.Context
import android.content.res.Configuration
import com.mytiki.tiki_sdk_android.TikiSdk.init
import com.mytiki.tiki_sdk_android.core.CoreChannel
import com.mytiki.tiki_sdk_android.core.req.*
import com.mytiki.tiki_sdk_android.core.rsp.*
import com.mytiki.tiki_sdk_android.ui.Offer
import com.mytiki.tiki_sdk_android.ui.Theme
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
object TikiSdk {

    /**
     * Returns a Boolean value indicating whether the TikiSdk has been initialized.
     *
     * If true, it means that the TikiSdk has been successfully initialized.
     * If false, it means that the TikiSdk has not yet been initialized or has failed to initialize.
     */
    val isInitialized: Boolean
        get() = address != null

    /**
     * A [Theme] object for pre-built UIs.
     *
     * @returns A [Theme] object with a dark mode appearance.
     */
    val theme = Theme()

    /**
     * A [Theme] object for pre-built UIs with a dark mode appearance.
     *
     * The dark mode theme is applied to the UI elements only when explicitly called. By default,
     * the dark mode theme is identical to the default (light) theme. Each individual property of
     * the dark mode theme can be customized during configuration.
     *
     * @returns A Theme object with a dark mode appearance or a default appearence if not set.
     */
    val dark: Theme
        get() {
            if (_dark == null) {
                _dark = Theme()
            }
            return _dark as Theme
        }

    /**
     * Creates a new, empty [Offer] object.
     *
     * This [Offer] object can be used to define a new offer before adding it to the TIKI SDK. To
     * add the offer, call the add() method on the [Offer] object.
     *
     * @returns A new, empty [Offer] object.
     */
    var offer: Offer = Offer()
        private set

    /**
     * A dictionary containing all the offers that have been added to the TIKI SDK.
     *
     * The offers are stored in the dictionary using their ID as the key. To retrieve an offer, use
     * its ID as the key to look up its corresponding [Offer] object in the dictionary.
     *
     * @returns A dictionary containing all the offers that have been added to the TIKI SDK.
     */
    var offers: MutableMap<String, Offer> = mutableMapOf()

    /**
     * A Boolean value that determines whether the ending UI is disabled for declined offers in the pre-built UI.
     *
     * If this property is set to true, the ending UI will not be shown when an offer is declined.
     * Otherwise, the ending UI will be shown as usual.
     *
     * @returns true if the ending UI is disabled for declined offers; otherwise, false`.
     */
    var isDeclineEndingDisabled: Boolean = false
        private set

    /**
     * A Boolean value that determines whether the ending UI is disabled for accepted offers in the pre-built UI.
     *
     * If this property is set to true, the ending UI will not be shown when an offer is accepted.
     * Otherwise, the ending UI will be shown as usual.
     *
     * @returns true if the ending UI is disabled for accepted offers; otherwise, false`.
     */
    var isAcceptEndingDisabled: Boolean = false
        private set


    /**
     * Adds an [Offer] object to the offers dictionary, using its ID as the key.
     *
     * The [Offer] object is added to the offers dictionary with its ID as the key. If an offer with
     * the same ID already exists in the dictionary, it will be overwritten by the new offer.
     *
     * @param offer: The [Offer] object to add to the offers dictionary.
     * @returns The TikiSdk instance.
     */
    fun addOffer(offer: Offer): TikiSdk {
        offers[offer.id] = offer
        return this
    }

    /**
     * Removes an Offer object from the offers dictionary, using its ID as the key.
     *
     * The Offer object with the specified ID is removed from the offers dictionary. If no offer with
     * the specified ID is found in the dictionary, this method has no effect.
     *
     * @param offerId: The ID of the Offer object to remove from the offers dictionary.
     * @returns The TikiSdk instance.
     */
    fun removeOffer(offerId: String) {
        offers.remove(offerId)
    }

    /**
     * Disables or enables the ending UI for accepted offers.
     *
     * If this method is called with a parameter  value of true, the ending UI will not be shown when
     * an offer is accepted. If the parameter  value is false, the ending UI will be shown as usual.
     *
     * @param disable: A Boolean  valueindicating whether the ending UI for accepted offers should
     * be disabled (`true`) or enabled (`false`).
     * @returns The TikiSdk instance.
     */
    fun disableAcceptEnding(disable: Boolean): TikiSdk {
        isAcceptEndingDisabled = disable
        return this
    }

    /**
     * Disables or enables the ending UI for declined offers.
     *
     * f this method is called with a parameter value of true, the ending UI will not be shown when
     * an offer is declined. If the parameter value is false, the ending UI will be shown as usual.
     *
     * @param disable: A Boolean  value indicating whether the ending UI for declined offers should
     * be disabled (`true`) or enabled (`false`).
     * @returns The TikiSdk instance.
     */
    fun disableDeclineEnding(disable: Boolean): TikiSdk {
        isDeclineEndingDisabled = disable
        return this
    }

    /**
     * Sets the callback function for when an offer is accepted.
     *
     * This method sets the onAccept event handler, which is triggered when the user accepts a licensing offer.
     *
     * @param onAccept: The closure to be executed when an offer is declined. The closure takes two
     * arguments: the Offer that was accepted, and the LicenseRecord object containing the license
     * information for the accepted offer.
     * @returns The TikiSdk instance.
     */
    fun onAccept(onAccept: ((Offer, LicenseRecord) -> Void)?): TikiSdk {
        _onAccept = onAccept
        return this
    }

    /**
     * Sets the callback function for when an offer is declined.
     *
     * This method sets the onDecline event handler, which is triggered when the user declines a licensing offer.
     * The event is triggered either when the offer flow is dismissed or when the user selects "Back Off".
     *
     * @param onDecline: The closure to be executed when an offer is declined. The closure takes two
     * arguments: the [Offer] that was declined, and an optional [LicenseRecord] object containing
     * the license information for the declined offer, if it was accepted before.
     *
     * @returns The TikiSdk instance.
     */
    fun onDecline(onDecline: ((Offer, LicenseRecord?) -> Void)?): TikiSdk {
        _onDecline = onDecline
        return this
    }

    /**
     * Sets the callback function for when the user selects "Settings" in the ending widget.
     *
     * This method sets the onSettings() event handler, which is triggered when the user selects
     * "Settings" in the ending screen.
     * If a callback function is not registered, the SDK will default to calling the TikiSdk.settings()
     * method.
     *
     * @param onSettings: The closure to be executed when the "Settings" option is selected.
     * The closure takes the Application Context as argument and returns no value
     *
     * @returns The TikiSdk instance.
     */
    fun onSettings(onSettings: (Context) -> Deferred<Unit>): TikiSdk {
        _onSettings = onSettings
        return this
    }

    /**
     * Initializes the TIKI SDK.
     *
     * Use this method to initialize the TIKI SDK with the specified  [publishingId],  [id],
     * and optionally [origin].
     * You can also provide an optional onComplete closure that will be executed once the initialization process is complete.
     *
     * @param publishingId: The  *publishingId* for connecting to the TIKI cloud.
     * @param id: The ID that uniquely identifies your user.
     * @param onComplete: An optional closure to be executed once the initialization process is complete.
     * @param origin: The default  *origin* for all transactions. Defaults to Bundle.main.bundleIdentifier if  *nil*.
     */
    fun init(
        context: Context,
        publishingId: String,
        id: String,
        origin: String?,
        onComplete: (() -> Void)?
    ): Deferred<Unit> {
        return MainScope().async {
            val loader = FlutterLoader()
            loader.startInitialization(context)
            yield()
            loader.ensureInitializationComplete(context, null)
            val flutterEngine = FlutterEngine(context)
            flutterEngine.dartExecutor.executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
            )
            GeneratedPluginRegistrant.registerWith(flutterEngine)
            val coreChannel = CoreChannel(context)
            this@TikiSdk.coreChannel = coreChannel
            flutterEngine.plugins.add(coreChannel)
            yield()
            val rspBuild = coreChannel
                .invokeMethod<RspInit, ReqInit>(
                    CoreMethod.BUILD,
                    ReqInit(publishingId, id, origin ?: context.packageName)
                ).await()
            this@TikiSdk.address = rspBuild!!.address
            onComplete?.let {
                it()
            }
        }
    }

    /**
     * Presents an Offer to the user and allows them to accept or decline it, which can result in a new LicenseRecord`
     * being created based on the presented Offer`.
     *
     * If the Offer has already been accepted by the user, this method does nothing.
     *
     * @param context
     * @throws IllegalStateException if the SDK is not initialized or if no Offer was created.
     */
    fun present(context: Context): Deferred<Unit> {
        throwIfNotInitialized()
        throwIfNoOffers()
        val presentOffer = { _: String? ->
            print("ok")
        }
        return MainScope().async {
            val ptr: String = offers.values.first().ptr
            val usecases: MutableList<LicenseUsecase> = mutableListOf()
            val destinations: MutableList<String> = mutableListOf()
            offers.values.first().uses.forEach {
                if (it.destinations != null) {
                    destinations.addAll(it.destinations)
                }
                usecases.addAll(it.usecases)
                guard(ptr, usecases, destinations, null, presentOffer)
            }
        }
    }

    /**
     * Presents the Tiki SDK's pre-built user interface for the settings screen, which allows the
     * user to accept or decline the current offer.
     *
     * @param context
     * @throws IllegalStateException if the SDK is not initialized or if no Offer was created.
     */
    fun settings(context: Context) {
        throwIfNotInitialized()
        throwIfNoOffers()
    }

    /**
     * Returns the Theme configured for the specified  *colorScheme*, or the default theme if none
     * is specified or the specified color scheme does not exist.
     *
     * If a dark theme has been defined and a color scheme of .dark is requested, the dark theme
     * will be returned instead of the default theme.
     *
     * @param context: The application context
     * @returns The Theme configured for the specified color scheme, or the default theme if none
     * is specified or the specified color scheme does not exist.
     */
    fun theme(context: Context): Theme {
        return when (context.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> if (_dark == null) theme else dark
            Configuration.UI_MODE_NIGHT_NO -> theme
            Configuration.UI_MODE_NIGHT_UNDEFINED -> theme
            else -> theme
        }
    }

    /**
     * Creates a new LicenseRecord object.
     *
     * The method searches for a TitleRecord object that matches the provided ptr parameter.
     * If such a record exists, the tags and titleDescription parameters are ignored. Otherwise,
     * a new TitleRecord is created using the provided tags and titleDescription parameters.
     *
     * If the origin parameter is not provided, the default origin specified in initialization is used.
     * The expiry parameter sets the expiration date of the LicenseRecord`. If the license never
     * expires, leave this parameter as null.
     *
     * @param ptr: The pointer record identifies data stored in your system, similar to a foreign key.
     * Learn more about selecting good pointer records at https://docs.mytiki.com/docs/selecting-a-pointer-record.
     * @param uses: A list defining how and where an asset may be used, in the format of LicenseUse
     * objects. Learn more about specifying uses at https://docs.mytiki.com/docs/specifying-terms-and-usage.
     * @param terms: The legal terms of the contract. This is a long text document that explains
     * the terms of the license.
     * @param tags: A list of metadata tags included in the TitleRecord describing the asset, for
     * your use in record search and filtering. This parameter is used only if a TitleRecord does
     * not already exist for the provided ptr`.
     * @param titleDescription: A short, human-readable description of the TitleRecord as a future
     * reminder. This parameter is used only if a TitleRecord does not already exist for the provided ptr`.
     * @param licenseDescription: A short, human-readable description of the LicenseRecord as a
     * future reminder.
     * @param expiry: The expiration date of the LicenseRecord`. If the license never expires,
     * leave this parameter as null.
     * @param origin: An optional override of the default origin specified in init()`. Use a
     * reverse-DNS syntax, e.g. com.myco.myapp`.
     *
     * @returns The created LicenseRecord object.
     *
     * @throws IllegalStateException if the SDK is not initialized
     */
    fun license(
        ptr: String, uses: List<LicenseUse>, terms: String, tags: List<TitleTag> = listOf(),
        titleDescription: String? = null, licenseDescription: String? = null,
        expiry: Date? = null, origin: String? = null
    ): Deferred<LicenseRecord> {
        throwIfNotInitialized()

        val licenseReq = ReqLicense(
            ptr, terms, titleDescription, licenseDescription, uses, tags, expiry, origin
        )

        val rspLicenseCompletable: CompletableDeferred<RspLicense?> =
            coreChannel!!.invokeMethod(
                CoreMethod.LICENSE, licenseReq
            )
        return MainScope().async {

            val rspLicense: RspLicense = rspLicenseCompletable.await()!!
            rspLicense.license!!
        }
    }

    /**
     * Guard against an in valid LicenseRecord for a list of usecases and destinations.
     *
     * Use this method to verify that a non-expired LicenseRecord for the specified pointer record
     * exists and permits the listed usecases and destinations.
     *
     * This method can be used in two ways:
     * 1. As an async traditional guard, returning a pass/fail boolean:
     * ``
     * val pass = guard(
     *    ptr: "example-ptr",
     *    usecases: [.attribution],
     *    destinations: ["https://example.com"])
     *  .await()
     * if(pass){
     *     // Perform the action allowed by the LicenseRecord.
     * }
     * ``
     * 2. As a wrapper around a function:
     * ``
     * guard(
     *    ptr: "example-ptr",
     *    usecases: [.attribution],
     *    destinations: ["https://example.com"]),
     *    onPass: {
     *     // Perform the action allowed by the LicenseRecord.
     * }, onFail: { error in
     *     // Handle the error.
     * })
     * ``
     *
     * @param ptr: The pointer record for the asset. Used to locate the latest relevant LicenseRecord.
     * @param usecases: A list of usecases defining how the asset will be used.
     * @param destinations: A list of destinations defining where the asset will be used, often URLs.
     * @param onPass: A closure to execute automatically upon successfully resolving the LicenseRecord against the usecases and destinations.
     * @param onFail: A closure to execute automatically upon failure to resolve the LicenseRecord. Accepts an optional error message describing the reason for failure.
     * @param origin: An optional override of the default origin specified in the initializer.
     *
     * @returns true if the user has access, false otherwise.
     */
    fun guard(
        ptr: String,
        usecases: List<LicenseUsecase> = listOf(),
        destinations: List<String> = listOf(),
        onPass: (() -> Void)? = null,
        onFail: ((String?) -> Unit)? = null,
        origin: String? = null
    ): Deferred<Boolean> {
        throwIfNotInitialized()
        val guardReq = ReqGuard(ptr, usecases, destinations, origin)
        val rspGuardCompletable: CompletableDeferred<RspGuard?> =
            coreChannel!!.invokeMethod(
                CoreMethod.GUARD, guardReq
            )
        return MainScope().async {
            val rspGuard: RspGuard = rspGuardCompletable.await()!!
            if (onPass != null && rspGuard.success) {
                onPass()
            }
            if (onFail != null && !rspGuard.success) {
                onFail(rspGuard.reason)
            }
            rspGuard.success
        }
    }

    /**
     * Creates a new TitleRecord, or retrieves an existing one.
     *
     * Use this function to create a new TitleRecord for a given Pointer Record (ptr), or retrieve
     * an existing one if it already exists.
     *
     * @param ptr: The Pointer Record that identifies the data stored in your system, similar to a
     * foreign key. Learn more about selecting good pointer records at
     * https://docs.mytiki.com/docs/selecting-a-pointer-record.
     *
     * @param origin: An optional override of the default origin specified in [init].
     * Follow a reverse-DNS syntax, i.e. com.myco.myapp.
     * @param tags: A list of metadata tags included in the TitleRecord describing the asset, for
     * your use in record search and filtering. Learn more about adding tags at
     * https://docs.mytiki.com/docs/adding-tags.
     * @param description: A short, human-readable, description of the TitleRecord as a future reminder.
     *
     * @returns The created or retrieved TitleRecord.
     */
    fun title(
        ptr: String, origin: String? = null, tags: List<TitleTag> = listOf(),
        description: String? = null
    ): Deferred<TitleRecord> {
        throwIfNotInitialized()
        val titleReq = ReqTitle(ptr, tags, description, origin)
        val rspTitleCompletable: CompletableDeferred<RspTitle?> =
            coreChannel!!.invokeMethod(
                CoreMethod.LICENSE, titleReq
            )
        return MainScope().async {
            val rspTitle: RspTitle = rspTitleCompletable.await()!!
            rspTitle.title!!
        }
    }

    /**
     * Retrieves the TitleRecord with the specified ID, or null if the record is not found.
     *
     * Use this method to retrieve the metadata associated with an asset identified by its TitleRecord ID.
     *
     * @param id: The ID of the TitleRecord to retrieve.
     * @param origin: An optional override of the default origin specified in initialization. Follow a
     *  reverse-DNS syntax, i.e.
     *  com.myco.myapp`.
     *  @returns The TitleRecord with the specified ID, or null if the record is not found.
     */
    fun getTitle(id: String, origin: String? = null): Deferred<TitleRecord?> {
        throwIfNotInitialized()
        val titleReq = ReqTitleGet(id, origin)
        val rspTitleCompletable: CompletableDeferred<RspTitle?> =
            coreChannel!!.invokeMethod(
                CoreMethod.LICENSE, titleReq
            )
        return MainScope().async {
            val rspTitle: RspTitle? = rspTitleCompletable.await()
            rspTitle?.title
        }
    }

    /**
     * Returns the LicenseRecord for a given ID or nil if the license or corresponding title record
     * is not found.
     *
     * This method retrieves the LicenseRecord object that matches the specified ID. If no record
     * is found, it returns nil. The origin parameter can be used to override the default origin
     * specified in initialization.
     *
     * @param id: The ID of the LicenseRecord to retrieve.
     * @param origin: An optional override of the default origin specified in initTikiSdkAsync`.
     * @returns The LicenseRecord that matches the specified ID or nil if the license or corresponding title record is not found.
     */
    fun getLicense(id: String, origin: String? = null): Deferred<LicenseRecord?> {
        throwIfNotInitialized()
        val licenseReq = ReqLicenseGet(id, origin)
        val rspLicenseCompletable: CompletableDeferred<RspLicense?> =
            coreChannel!!.invokeMethod(
                CoreMethod.LICENSE, licenseReq
            )
        return MainScope().async {
            val rspLicense: RspLicense = rspLicenseCompletable.await()!!
            rspLicense.license
        }
    }

    /**
     * Returns all LicenseRecords associated with a given Pointer Record.
     *
     * Use this method to retrieve all LicenseRecords that have been previously stored for a given
     * Pointer Record in your system.
     *
     * @param ptr: The Pointer Record that identifies the data stored in your system, similar to a
     * foreign key.
     * @param origin: An optional origin. If nil, the origin defaults to the package name.
     *
     * @returns An array of all LicenseRecords associated with the given Pointer Record. If no
     * LicenseRecords are found, an empty array is returned.
     */
    fun all(ptr: String, origin: String? = null): Deferred<List<LicenseRecord>> {
        throwIfNotInitialized()
        val licenseReq = ReqLicenseAll(ptr, origin)
        val rspLicenseCompletable: CompletableDeferred<RspLicenseList?> =
            coreChannel!!.invokeMethod(
                CoreMethod.LICENSE, licenseReq
            )
        return MainScope().async {
            val rspLicense: RspLicenseList = rspLicenseCompletable.await()!!
            rspLicense.licenseList
        }
    }

    /**
     * Returns the latest LicenseRecord for a ptr or nil if the corresponding title or license
     * records are not found.
     *
     * @param ptr: The Pointer Records identifies data stored in your system, similar to a foreign key.
     * @param origin: An optional origin. If nil, the origin defaults to the package name.
     *
     * @returns The latest [LicenseRecord] for the given ptr, or nil if the corresponding title or
     * license records are not found.
     */
    fun latest(ptr: String, origin: String? = null): Deferred<LicenseRecord?> {
        throwIfNotInitialized()
        val licenseReq = ReqLicenseLatest(ptr, origin)
        val rspLicenseCompletable: CompletableDeferred<RspLicense?> =
            coreChannel!!.invokeMethod(
                CoreMethod.LICENSE, licenseReq
            )
        return MainScope().async {
            val rspLicense: RspLicense = rspLicenseCompletable.await()!!
            rspLicense.license
        }
    }

    private var address: String? = null
    private var coreChannel: CoreChannel? = null
    private var _dark: Theme? = null
    private var _onAccept: ((Offer, LicenseRecord) -> Void)? = null
    private var _onDecline: ((Offer, LicenseRecord?) -> Void)? = null
    private var _onSettings: (Context) -> Deferred<Unit> = {
        MainScope().async {
            settings(it)
        }
    }

    private fun throwIfNotInitialized() {
        if (!isInitialized) {
            throw IllegalStateException("Please ensure that the TIKI SDK is properly initialized by calling initialize().")
        }
    }

    private fun throwIfNoOffers() {
        if (offers.isEmpty()) {
            throw IllegalStateException("To proceed, kindly utilize the TikiSdk.offer() method to include at least one offer.")
        }
    }
}