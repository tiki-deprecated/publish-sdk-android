import com.mytiki.tiki_sdk_android.TitleTag
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Title record.
 *
 * Title Records describe a data asset and MUST contain a Pointer Record to your system.
 * [Learn more](https://docs.mytiki.com/docs/offer-customization) about Title Records.
 *
 * @param id This record's id.
 * @param hashedPtr A Pointer Record identifying the asset.
 * @param tags A list of search-friendly tags describing the asset.
 * @param description A human-readable description of the asset.
 * @param origin Overrides the default origin from which the data was generated.
 */
@JsonClass(generateAdapter = true)
data class TitleRecord(
    val id: String,
    @Json(name = "ptr") val hashedPtr: String,
    val tags: List<TitleTag>,
    val description: String?,
    val origin: String?
)
