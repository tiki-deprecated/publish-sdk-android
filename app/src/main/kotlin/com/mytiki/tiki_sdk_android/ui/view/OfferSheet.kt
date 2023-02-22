import android.content.Context
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mytiki.tiki_sdk_android.ui.model.Offer

/**
 * Offer sheet
 *
 * A dismissible bottom sheet that shows an [Offer] to the user.
 * There are 4 available actions in this screen:
 * - learnMore: will show the [TextViewer] screen with [learnMoreText] loaded.
 * - deny: will show the [CompletionSheet.backoff].
 * - allow: will show the [CompletionSheet.awesome]. If [requireTerms] is
 * true, it will show the [TextViewer] with [termsText] for the user accept the
 * terms before the [CompletionSheet.awesome] screen is shown.
 *
 * @property offer
 * @property requireTerms
 * @property termsText
 * @property learnMoreText
 * @constructor
 *
 * @param context
 */


class OfferSheet(
    context: Context,
    private val offer: Offer,
    private val requireTerms: Boolean,
    private val termsText: String?,
    private val learnMoreText: String?
) : BottomSheetDialog(context)