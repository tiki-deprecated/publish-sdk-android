/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.core.req

import com.mytiki.tiki_sdk_android.LicenseUsecase

data class ReqGuard(
    var ptr: String? = null,
    var usecases: List<LicenseUsecase> = emptyList(),
    var destinations: List<String>? = null,
    var origin: String? = null
) {
    fun toJson(): String {
        val builder = StringBuilder()
        builder.append("{")
        builder.append("\"ptr\":").append("\"").append(ptr).append("\"").append(",")
        builder.append("\"usecases\":").append("[")
        for (i in usecases.indices) {
            builder.append(usecases[i].toJson())
            if (i != usecases.size - 1) {
                builder.append(",")
            }
        }
        builder.append("]").append(",")
        builder.append("\"destinations\":").append("[")
        if (destinations != null) {
            for (i in destinations!!.indices) {
                builder.append("\"").append(destinations!![i]).append("\"")
                if (i != destinations!!.size - 1) {
                    builder.append(",")
                }
            }
        }
        builder.append("]").append(",")
        builder.append("\"origin\":").append("\"").append(origin).append("\"")
        builder.append("}")
        return builder.toString()
    }
}
