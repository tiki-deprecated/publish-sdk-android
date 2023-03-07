/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android

import android.Manifest
import android.graphics.Bitmap
import com.mytiki.tiki_sdk_android.ui.UsedBullet

import java.util.*

/**
 * An Offer for creating a [LicenseRecord] for a [TitleRecord] identified by [ptr].
 */
class Offer {
    private var _id: String? = null
    private var _ptr: String? = null
    private var _description: String? = null
    private var _terms: String? = null
    private var _reward: Bitmap? = null
    private var _usedBullet = mutableListOf<UsedBullet>()
    private var _uses = mutableListOf<LicenseUse>()
    private var _tags = mutableListOf<TitleTag>()
    private var _requiredPermissions = mutableListOf<Manifest.permission>()
    private var _expiry: Date? = null

    /**
     * The Offer unique identifier. If none is set, it creates a random UUID.
     */
    val id: String
        get() {
            if (_id == null) {
                _id = UUID.randomUUID().toString()
            }
            return _id!!
        }

    /**
     * An image that represents the reward.
     *
     * It should have 300x86 size and include assets for all screen depths.
     */
    val reward: Bitmap?
        get() = _reward

    /**
     * The bullets that describes how the user data will be used.
     */
    val usedBullet: List<UsedBullet>
        get() = _usedBullet

    /**
     * The Pointer Record of the data stored.
     */
    val ptr: String
        get() = _ptr!!

    /**
     * A human-readable description for the license.
     */
    val description: String?
        get() = _description

    /**
     * The legal terms of the offer.
     */
    val terms: String?
        get() = _terms

    /**
     * The Use cases for the license.
     */
    val uses: List<LicenseUse>
        get() = _uses

    /**
     * The tags that describes the represented data asset.
     */
    val tags: List<TitleTag>
        get() = _tags

    /**
     * The expiration of the License. Null for no expiration.
     */
    val expiry: Date?
        get() = _expiry

    /**
     * A list of device-specific [Permission] required for the license.
     */
    val requiredPermissions: List<Manifest.permission>
        get() = _requiredPermissions

    /**
     * Sets the [id]
     */
    fun setId(id: String): Offer {
        _id = id
        return this
    }

    /**
     * Sets the [reward]
     */
    fun setReward(reward: Bitmap): Offer {
        _reward = reward
        return this
    }

    /**
     * Sets the [usedBullet]
     */
    fun setUsedBullet(usedBullet: List<UsedBullet>): Offer {
        _usedBullet = usedBullet.toMutableList()
        return this
    }

    /**
     * Adds a [usedBullet]
     */
    fun addUsedBullet(usedBullet: UsedBullet): Offer {
        _usedBullet.add(usedBullet)
        return this
    }

    /**
     * Sets the [ptr]
     */
    fun setPtr(ptr: String): Offer {
        _ptr = ptr
        return this
    }

    /**
     * Sets the [description]
     */
    fun setDescription(description: String): Offer {
        _description = description
        return this
    }

    /**
     * Set terms
     *
     * @param terms
     * @return this Offer
     */
    fun setTerms(terms: String?): Offer {
        _terms = terms
        return this
    }

    /**
     * Set uses
     *
     * @param uses
     * @return this Offer
     */
    fun setUses(uses: List<LicenseUse>): Offer {
        _uses = uses.toMutableList()
        return this
    }

    /**
     * Adds an item to the [uses] list.
     *
     * @param use
     * @return this Offer
     */
    fun addUse(use: LicenseUse): Offer {
        _uses.add(use)
        return this
    }

    /**
     * Set tags
     *
     * @param tags
     * @return this Offer
     */
    fun setTags(tags: List<TitleTag>): Offer {
        _tags = tags.toMutableList()
        return this
    }

    ///

    /**
     * Adds an item to the [tags] list.
     *
     * @param tag
     * @return this Offer
     */
    fun addTag(tag: TitleTag): Offer {
        _tags.add(tag)
        return this
    }

    /**
     * Set the expiration of the [LicenseRecord]
     *
     * @param expiry
     * @return this Offer
     */
    fun setExpiry(expiry: Date?): Offer {
        _expiry = expiry
        return this
    }

    /// Sets the [requiredPermissions]

    /**
     * Set required permissions
     *
     * @param permissions
     * @return this Offer
     */
    fun setRequiredPermissions(permissions: List<Manifest.permission>): Offer {
        _requiredPermissions = permissions.toMutableList()
        return this
    }

    /**
     * Adds an item to the [requiredPermissions] list.
     *
     * @param permission
     * @return
     */
    fun addRequiredPermission(permission: Manifest.permission): Offer {
        _requiredPermissions.add(permission)
        return this
    }

    /**
     * Adds the built Offer to the [TikiSdk.offers] list
     *
     * @return TikiSdk
     */
    @Throws(IllegalArgumentException::class)
    fun add(): TikiSdk {
        if (_ptr == null) {
            throw IllegalArgumentException("Set the Offer pointer record (ptr).")
        }
        if (_uses.isEmpty()) {
            throw IllegalArgumentException("Add at least one License use case to the Offer.")
        }
        return TikiSdk.addOffer(this)
    }
}

