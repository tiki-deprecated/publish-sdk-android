package com.mytiki.tiki_sdk_android

import TitleRecord
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat.startActivity
import com.mytiki.tiki_sdk_android.tiki_platform_channel.MethodEnum
import com.mytiki.tiki_sdk_android.tiki_platform_channel.TikiPlatformChannel
import com.mytiki.tiki_sdk_android.tiki_platform_channel.req.ReqBuild
import com.mytiki.tiki_sdk_android.tiki_platform_channel.rsp.RspBuild
import com.mytiki.tiki_sdk_android.ui.TikiSdkActivity
import kotlinx.coroutines.*


/**
 * The TIKI SDK main class. Use this to add tokenized data ownership, consent, and rewards.
 */
object TikiSdk {

    var address: String? = null
    private var onAccept: ((Offer) -> Unit)? = null
    private var onDecline: ((Offer) -> Unit)? = null
    private var onSettings: ((Offer) -> Unit)? = null
    private var isAcceptEndingDisabled = false
    private var isDeclineEndingDisabled = false
    private val offers = mutableMapOf<String, Offer>()
    private val localTheme = Theme()
    private var localDark: Theme? = null
    private var tikiPlatformChannel: TikiPlatformChannel? = null

    /**
     * Returns the default [Theme] for the TikiSdk.
     */
    val theme: Theme
        get() = localTheme

    /**
     * Returns the default dark [Theme] for the TikiSdk.
     */
    val dark: Theme
        get() {
            if (localDark == null) {
                localDark = Theme(dark = true)
            }
            return localDark!!
        }

    /**
     * Returns a new [Offer] instance.
     */
    val offer: Offer
        get() = Offer()

    /**
     * Returns the appropriate [Theme] based on the current [Configuration].
     *
     * @param configuration The activity [Configuration].
     * @return The appropriate [Theme].
     */
    fun getActiveTheme(configuration: Configuration): Theme {
        val currentNightMode = configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES && localDark != null) {
            return localDark as Theme
        }
        return theme
    }

    /**
     * Present the TikiSdkActivity
     *
     * @param context The context in which the TikiSdk view should be presented.
     */
    fun present(context: Context) {
        startActivity(context, Intent(context, TikiSdkActivity::class.java), null)
    }

    /**
     * Shows the pre-built Settings UI.
     * @param context The [UIViewController] in which the Settings UI should be presented.
     */
    fun settings(context: Context) {
        // TODO
    }

    /**
     * Start the TikiSdk configuration.
     * @return The TikiSdk instance.
     */
    fun config(): TikiSdk {
        return this
    }

    /**
     * Adds a new [Offer] for the user.
     *
     * @param offer The [Offer] to add.
     * @return The [TikiSdk] instance.
     */
    fun addOffer(offer: Offer): TikiSdk {
        offers[offer.id] = offer
        return this
    }

    /**
     * Disables the ending screen for accepted [Offer].
     *
     * @param disable Whether to disable the ending screen.
     * @return The [TikiSdk] instance.
     */
    fun disableAcceptEnding(disable: Boolean): TikiSdk {
        isAcceptEndingDisabled = disable
        return this
    }

    /**
     * Disables the ending screen for declined [Offer].
     *
     * @param disable Whether to disable the ending screen.
     * @return The [TikiSdk] instance.
     */
    fun disableDeclineEnding(disable: Boolean): TikiSdk {
        isDeclineEndingDisabled = disable
        return this
    }

    /**
     * Sets the callback function for an accepted [Offer].
     *
     * The [onAccept] event is triggered on the user's successful acceptance
     * of the licensing offer. This happens after accepting the terms, not just
     * on selecting "I'm In." The [Offer] is passed as a parameter to the
     * callback function.
     *
     * @param onAccept The callback function to set.
     * @return The [TikiSdk] instance.
     */
    fun setOnAccept(onAcceptCall: ((Offer) -> Unit)?): TikiSdk {
        onAccept = onAcceptCall
        return this
    }

    /**
     * Sets the callback function for a declined [Offer].
     *
     * The [onDecline] event is triggered when the user declines the licensing offer.
     * This happens on dismissal of the flow or when "Back Off" is selected.
     *
     * @param onDecline The callback function to set.
     * @return The [TikiSdk] instance.
     */
    fun setOnDecline(onDeclineCall: ((Offer) -> Unit)?): TikiSdk {
        onDecline = onDeclineCall
        return this
    }

    /**
     * Sets the callback function for user selecting the "settings" option in ending widget.
     *
     * The [onSettings] event is triggered when the user selects "settings" in the
     * ending screen. If a callback function is not registered, the SDK defaults to
     * calling the [TikiSdk.settings] method.
     *
     * @param onSettings The callback function to set.
     * @return The [TikiSdk] instance.
     */
    fun setOnSettings(onSettingsCall: ((Offer) -> Unit)?): TikiSdk {
        onSettings = onSettingsCall
        return this
    }

    /**
     * Initializes the TIKI SDK.
     * @param publishingId The publishingId for connecting to TIKI cloud.
     * @param address The address of the user node in TIKI blockchain. If null, a new address will be created.
     * @param origin The default origin for all transactions.
     */
    fun init(
        context: Context,
        publishingId: String,
        address: String? = null,
        origin: String? = null
    ): Deferred<TikiSdk> {
        return MainScope().async {
            if (tikiPlatformChannel == null) {
                tikiPlatformChannel = TikiPlatformChannel(context)
            }
            val rspBuild: RspBuild? = tikiPlatformChannel!!.invokeMethod<RspBuild, ReqBuild>(
                MethodEnum.BUILD,
                ReqBuild(publishingId, origin ?: context.packageName, address)
            ).await()
            this@TikiSdk.address = rspBuild!!.address
            return@async this@TikiSdk
        }
    }

    /**
     * Create a new [LicenseRecord].
     *
     * If a [TitleRecord] for the [Offer.ptr] is not found, a new [TitleRecord] is created.
     * If a [TitleRecord] is found, [Offer.tags] and [Offer.description] parameters are ignored.
     *
     * @param offer
     * @param accepted
     * @return
     */
    suspend fun license(offer: Offer) : LicenseRecord{
        // TODO
        // String ptr => Offer.ptr
        // List<LicenseUse> uses => Offer.uses
        // String terms => Offer.terms
        // List<TitleTag> tags => Offer.tags
        // String? licenseDescription => ?? Offer.description
        // DateTime? expiry}
    }

    /**
     * Guard against an invalid [LicenseRecord] for a List of [usecases] an [destinations].
     *
     * Use this method to verify a non-expired, [LicenseRecord] for the [ptr]
     * exists, and permits the listed [usecases] and [destinations].
     *
     * This method can be used in two forms,
     * 1) async as a traditional guard, returning a pass/fail boolean. Or
     * 2) as a wrapper around function.
     *
     * For example: An http that you want to run IF permitted by a [LicenseRecord].
     *
     * Option 1:
     * ```
     * val pass = guard("ptr", [LicenseUsecase.attribution()]).await();
     * if(pass) http.post(...);
     * ```
     *
     * Option 2:
     * ```
     * guard('ptr', [LicenseUsecase.attribution()], onPass: () => http.post(...));
     * ```
     *
     * @param ptr The Pointer Record for the asset. Used to located the latest relevant [LicenseRecord].
     * @param usecases A List of usecases defining how the asset will be used.
     * @param destinations A List of destinations defining where the asset will be used. _Often URLs_
     * @param onPass - A Function to execute automatically upon successfully resolving the [LicenseRecord]
     * against the [usecases] and [destinations].
     * @param onFail A Function to execute automatically upon failure to resolve the [LicenseRecord].
     * Accepts a String parameter, holding an error message describing the reason for failure.
     * @param origin An optional override of the default [origin] specified in.
     * @return True if the user has access, false otherwise.
     */
    suspend fun guard(
        ptr: String,
        usecases: List<LicenseUseCase>,
        destinations: List<String>,
        onPass: (() -> Unit)? = null,
        onFail: (() -> Unit)? = null,
        origin: String? = null
    ): Boolean {
        // TODO
        // String ptr => Offer.ptr
        // List<LicenseUsecase> usecases => uses.usecases
        // List<String>? destinations => uses.destinations
        return true;
    }


    /**
     * Create a new [TitleRecord].
     * @param ptr - The Pointer Records identifies data stored in your system,
     * similar to a foreign key.
     * [Learn more](https://docs.mytiki.com/docs/selecting-a-pointer-record)
     * about selecting good pointer records.
     * @param origin  - An optional override of the default [origin] specified in
     * [initTikiSdkAsync]. Follow a reverse-DNS syntax. _i.e. com.myco.myapp_
     * @param tags - A `List` of metadata tags included in the [TitleRecord]
     * describing the asset, for your use in record search and filtering.
     * [Learn more](https://docs.mytiki.com/docs/adding-tags)
     * about adding tags.
     * @param description - A short, human-readable, description of
     * the [TitleRecord] as a future reminder.
     * @return the created [TitleRecord]
     */
    suspend fun title(ptr: String, origin: String? = null, tags: List<TitleTag>? = listOf(),
                      description: String? = null) : TitleRecord{}

    /**
     * Get title
     *
     * Returns the [TitleRecord] for an [id] or null if the record is
     * not found.
     *
     * @param id
     * @return [TitleRecord]?
     */
    suspend fun getTitle(id: String) :  TitleRecord?{}

    /**
     * Get license
     *
     * Returns the [LicenseRecord] for an [id] or null if the license
     * or corresponding title record is not found.
     *
     * @param id
     * @return [LicenseRecord]?
     */
    suspend fun getLicense(id: String): LicenseRecord?{}

    /**
     * Returns all [LicenseRecord]s for a [ptr].
     *
     * Optionally, an [origin] may be specified. If null [origin] defaults to the package name.
     *
     * The [LicenseRecord]s returned may be expired or not applicable to a specific [LicenseUse].
     * To check license validity, use the [guard] method.
     *
     * @param ptr
     * @param origin
     * @return
     */
    suspend fun all(ptr: String, origin: String? = null): List<LicenseRecord> {}

    /**
     * Returns the latest [LicenseRecord] for a [ptr] or null if the
     * title or license records are not found.
     *
     * Optionally, an [origin] may be specified. If null [origin] defaults to the package name.
     *
     * The [LicenseRecord] returned may be expired or not applicable to a
     * specific [LicenseUse]. To check license validity, use the [guard]
     * method.

     *
     * @param ptr
     * @param origin
     * @return
     */
    suspend fun latest(ptr:String, origin: String? = null) : LicenseRecord? {}




}
    

