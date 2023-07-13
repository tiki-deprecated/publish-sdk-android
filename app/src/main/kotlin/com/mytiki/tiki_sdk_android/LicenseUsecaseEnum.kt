/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android

/**
 * Default accepted usecases
 *
 * @property value
 * @constructor Create a new License usecase from [value]
 */
enum class LicenseUsecaseEnum(val value: String) {
    ATTRIBUTION("attribution"),
    RETARGETING("retargeting"),
    PERSONALIZATION("personalization"),
    AI_TRAINING("ai_training"),
    DISTRIBUTION("distribution"),
    ANALYTICS("analytics"),
    SUPPORT("support");

    companion object {
        /**
         * Builds a [LicenseUsecaseEnum] from [value]
         * @throws [IllegalArgumentException] if value is not a valid [LicenseUsecaseEnum] value
         */
        @Throws(IllegalArgumentException::class)
        fun fromValue(value: String): LicenseUsecaseEnum {
            for (type in values()) {
                if (type.value == value) {
                    return type
                }
            }
            throw IllegalArgumentException("Invalid Enum value: $value")
        }
    }
}