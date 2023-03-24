/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android

/**
 * Default accepted usecases
 */
enum class LicenseUsecaseEnum(val value: String) {
    ATTRIBUTION("attribution"),
    RETARGETING("retargeting"),
    PERSONALIZATION("personalization"),
    AI_TRAINING("ai_training"),
    DISTRIBUTION("distribution"),
    ANALYTICS("analytics"),
    SUPPORT("support");

    /**
     * Builds a `LicenseUsecaseEnum` from `value`.
     *
     * @param value string value of enum.
     * @return `LicenseUsecaseEnum`
     * @throws IllegalArgumentException if `value` is not a valid `LicenseUsecaseEnum` value.
     */
    companion object {
        fun fromValue(value: String): LicenseUsecaseEnum {
            for (type in values()) {
                if (type.value == value) {
                    return type
                }
            }
            throw IllegalArgumentException("Invalid LicenseUsecaseEnum value $value")
        }
    }
}
