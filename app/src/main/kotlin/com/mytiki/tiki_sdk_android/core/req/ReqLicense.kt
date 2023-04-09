/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.core.req

import com.mytiki.tiki_sdk_android.LicenseUse
import com.mytiki.tiki_sdk_android.TitleTag
import java.util.*

data class ReqLicense(
    val ptr: String?,
    val terms: String?,
    val titleDescription: String?,
    val licenseDescription: String?,
    val uses: List<LicenseUse> = emptyList(),
    val tags: List<TitleTag> = emptyList(),
    val expiry: Date?,
    val origin: String?
) {
    fun toJson(): String {
        val builder = StringBuilder()
        builder.append("{")
        builder.append("\"ptr\":").append("\"").append(ptr).append("\"").append(",")
        builder.append("\"terms\":").append("\"").append(terms).append("\"").append(",")
        builder.append("\"titleDescription\":").append("\"").append(titleDescription).append("\"")
            .append(",")
        builder.append("\"licenseDescription\":").append("\"").append(licenseDescription)
            .append("\"").append(",")
        builder.append("\"uses\":").append("\"").append(uses).append("\"").append(",")
        builder.append("\"uses\":").append("[")
        for (i in uses.indices) {
            builder.append(uses[i].toJson())
            if (i != uses.size - 1) {
                builder.append(",")
            }
        }
        builder.append("]").append(",")
        builder.append("\"tags\":").append("[")
        for (i in tags.indices) {
            builder.append(tags[i].toJson())
            if (i != tags.size - 1) {
                builder.append(",")
            }
        }
        builder.append("]").append(",")
        builder.append("\"expiry\":").append("\"").append(expiry).append("\"").append(",")
        builder.append("\"origin\":").append("\"").append(origin).append("\"").append(",")
        builder.append("}")
        return builder.toString()
    }
}