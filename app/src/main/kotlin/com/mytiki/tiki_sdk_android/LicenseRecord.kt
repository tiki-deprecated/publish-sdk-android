/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android

import java.util.Date

/**
 * License Records describe the terms around which a data asset may be used and MUST
 * contain a reference to the corresponding Title Record.
 * [Learn more](https://docs.mytiki.com/docs/offer-customization)  about License Records.
 */
data class LicenseRecord(
    var id: String?,
    var title: TitleRecord,
    var uses: List<LicenseUse>,
    var terms: String,
    var description: String?,
    var expiry: Date?
)
