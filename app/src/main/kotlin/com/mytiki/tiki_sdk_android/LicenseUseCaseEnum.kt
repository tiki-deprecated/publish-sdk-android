package com.mytiki.tiki_sdk_android

/**
 * Default accepted usecases
 */
enum class LicenseUseCaseEnum(private val _value: String) {
    ATTRIBUTION("attribution"),
    RETARGETING("retargeting"),
    PERSONALIZATION("personalization"),
    AI_TRAINING("ai_training"),
    DISTRIBUTION("distribution"),
    ANALYTICS("analytics"),
    SUPPORT("support");

    /**
     * Returns the string value for the enum
     */
    val value: String
        get() = _value

    companion object {
        /**
         * Builds a [LicenseUseCaseEnum] from [value]
         * @throws [IllegalArgumentException] if value is not a valid [LicenseUseCaseEnum] value
         */
        @Throws(IllegalArgumentException::class)
        fun fromValue(value: String): LicenseUseCaseEnum {
            for (type in values()) {
                if (type.value == value) {
                    return type
                }
            }
            throw IllegalArgumentException("Invalid LicenseUsecaseEnum value $value")
        }
    }
}