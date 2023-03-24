/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import com.mytiki.tiki_sdk_android.TitleTag
import com.squareup.moshi.JsonClass


/**
 * Title record
 *
 * Title Records describe a data asset and MUST contain a Pointer Record to
 * your system. [Learn more](https://docs.mytiki.com/docs/offer-customization)
 * about Title Records.
 *
 * @property id This record's id.
 * @property ptr A Pointer Record identifying the asset.
 * @property tags A list of search-friendly tags describing the asset.
 * @property description A human-readable description of the asset.
 * @property origin Overrides the default origin from which the data was generated.
 * @constructor Create empty Title record
 */
@JsonClass(generateAdapter = true)
data class TitleRecord(
    /**
     * This record's id.
     */
    val id: String,
    /**
     * A Pointer Record identifying the asset
     */
    val ptr: String,
    /**
     * A list of search-friendly tags describing the asset.
     */
    val tags: List<TitleTag> = emptyList(),
    /**
     * A human-readable description of the asset.
     */
    val description: String? = null,
    /**
     * The origin from which the data was generated.
     */
    val origin: String? = null,
)
