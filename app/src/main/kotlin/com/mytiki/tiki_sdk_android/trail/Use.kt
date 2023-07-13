/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.trail

data class Use(
    var usecases: List<Usecase>,
    var destinations: List<String>? = null
) {
    fun map(): Map<String, Any?> {
        return mapOf(
            "usecases" to usecases.map { usecase -> usecase.value },
            "destinations" to destinations
        )
    }

    companion object {
        fun from(map: Map<String, Any?>): Use {
            val usecases: List<String> = (map["usecases"] as List<String>?) ?: emptyList()
            return Use(
                usecases.map { usecase -> Usecase.from(usecase) },
                map["destinations"] as List<String>?
            )
        }
    }
}