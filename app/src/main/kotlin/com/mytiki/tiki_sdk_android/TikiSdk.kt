/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import com.mytiki.tiki_sdk_android.TikiSdk.initialize
import com.mytiki.tiki_sdk_android.channel.Channel
import com.mytiki.tiki_sdk_android.idp.Idp
import com.mytiki.tiki_sdk_android.trail.LicenseRecord
import com.mytiki.tiki_sdk_android.trail.Trail
import com.mytiki.tiki_sdk_android.trail.Usecase
import com.mytiki.tiki_sdk_android.trail.rsp.RspInitialize
import com.mytiki.tiki_sdk_android.ui.Offer
import com.mytiki.tiki_sdk_android.ui.Theme
import com.mytiki.tiki_sdk_android.ui.activities.OfferFlowActivity
import com.mytiki.tiki_sdk_android.ui.activities.SettingsActivity
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async

/**
 * The TIKI SDK main class. Use this to add tokenized data ownership, consent, and rewards.
 *
 * @constructor Create empty Tiki sdk. [initialize] should be called to build the SDK.
 */
object TikiSdk {

    private val channel: Channel = Channel()
    val idp: Idp = Idp(channel)
    val trail: Trail = Trail(channel)

    private var _address: String? = null
    /**
     * Returns a Boolean value indicating whether the TikiSdk has been initialized.
     *
     * If true, it means that the TikiSdk has been successfully initialized.
     * If false, it means that the TikiSdk has not yet been initialized or has failed to initialize.
     */
    val isInitialized: Boolean
        get() = _address != null
    val address: String
        get() {
            throwIfNotInitialized()
            return _address!!
        }

    private var _id: String? = null
    val id: String
        get() {
            throwIfNotInitialized()
            return _id!!
        }

    /**
     * A [Theme] object for pre-built UIs.
     *
     * @returns A [Theme] object with a dark mode appearance.
     */
    val theme = Theme()
    private var _dark: Theme? = null
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

    private var _onAccept: ((Offer, LicenseRecord) -> Unit)? = null
    private var _onDecline: ((Offer, LicenseRecord?) -> Unit)? = null
    private var _onSettings: (Context) -> Deferred<Unit> = {
        MainScope().async {
            settings(it)
        }
    }

    fun initialize(
        id: String,
        publishingId: String,
        context: Context,
        onComplete: (() -> Unit)? = null
    ): Deferred<Unit> {
        return MainScope().async {
            val rsp: RspInitialize = channel.initialize(id, publishingId, context).await()
            this@TikiSdk._address = rsp.address
            this@TikiSdk._id = rsp.id
            onComplete?.let { it() }
        }
    }

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
    fun onAccept(onAccept: ((Offer, LicenseRecord) -> Unit)?): TikiSdk {
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
    fun onDecline(onDecline: ((Offer, LicenseRecord?) -> Unit)?): TikiSdk {
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
     * Presents an Offer to the user and allows them to accept or decline it, which can result in a new LicenseRecord`
     * being created based on the presented Offer`.
     *
     * If the Offer has already been accepted by the user, this method does nothing.
     *
     * @param context
     * @throws IllegalStateException if the SDK is not initialized or if no Offer was created.
     */
    @Suppress("DeferredResultUnused")
    fun present(context: Context) {
        throwIfNotInitialized()
        throwIfNoOffers()
        MainScope().async {
            val ptr: String = offers.values.first().ptr
            val usecases: MutableList<Usecase> = mutableListOf()
            val destinations: MutableList<String> = mutableListOf()
            offers.values.first().uses.forEach {
                destinations.addAll(it.destinations ?: emptyList())
                usecases.addAll(it.usecases)
            }
            trail.guard(ptr, usecases, destinations, {
                Log.d("TIKI SDK", "Offer already accepted. PTR: $ptr")
            }, {
                val bundle = Bundle()
                val intent = Intent(context, OfferFlowActivity::class.java)
                bundle.putSerializable("theme", theme(context))
                intent.putExtras(bundle)
                startActivity(context, intent, null)
            })
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
        val bundle = Bundle()
        val intent = Intent(context, SettingsActivity::class.java)
        bundle.putSerializable("theme", theme(context))
        intent.putExtras(bundle)
        startActivity(context, intent, null)
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

    private fun throwIfNotInitialized() {
        if (!isInitialized) {
            throw IllegalStateException("Please ensure that the TIKI SDK is properly initialized by calling TikiSdk.init().")
        }
    }

    private fun throwIfNoOffers() {
        if (offers.isEmpty()) {
            throw IllegalStateException("To proceed, kindly utilize the TikiSdk.offer() method to include at least one offer.")
        }
    }
}
