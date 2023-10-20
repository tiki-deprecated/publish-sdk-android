/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.mytiki.tiki_sdk_android.ui

import android.Manifest
import android.content.Context
import android.graphics.drawable.Drawable
import com.mytiki.tiki_sdk_android.*
import com.mytiki.tiki_sdk_android.trail.Tag
import com.mytiki.tiki_sdk_android.trail.Use
import com.mytiki.tiki_sdk_android.trail.Usecase
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * An Offer for creating a [LicenseRecord] for a title identified by [ptr].
 */
class Offer {
    private var _id: String? = null
    private var _ptr: String? = null
    private var _description: String? = null
    private var _terms: String? = null
    private var _reward: Drawable? = null
    private var _usedBullet = mutableListOf<Bullet>()
    private var _uses = mutableListOf<Use>()
    private var _tags = mutableListOf<Tag>()
    private var _permissions = mutableListOf<Permission>()
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
    val reward: Drawable?
        get() = _reward

    /**
     * The bullets that describes how the user data will be used.
     */
    val bullets: List<Bullet>
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
    val terms: String
        get() = _terms!!

    /**
     * The Use cases for the license.
     */
    val uses: List<Use>
        get() = _uses

    /**
     * The tags that describes the represented data asset.
     */
    val tags: List<Tag>
        get() = _tags

    /**
     * The expiration of the License. Null for no expiration.
     */
    val expiry: Date?
        get() = _expiry

    /**
     * A list of device-specific [Manifest.permission] required for the license.
     */
    val permissions: List<Permission>
        get() = _permissions

    /**
     * Sets the [id]
     */
    fun id(id: String): Offer {
        _id = id
        return this
    }

    /**
     * Sets the [reward]
     */
    fun reward(reward: Drawable): Offer {
        _reward = reward
        return this
    }

    /**
     * Adds a [Bullet]
     */
    fun bullet(text: String, used: Boolean): Offer {
        _usedBullet.add(Bullet(text, used))
        return this
    }

    /**
     * Sets the [ptr]
     */
    fun ptr(ptr: String): Offer {
        _ptr = ptr
        return this
    }

    /**
     * Sets the [description]
     */
    fun description(description: String): Offer {
        _description = description
        return this
    }

    /**
     * Set terms
     *
     * @param context
     * @param filename
     * @return this Offer
     */
    fun terms(context: Context, filename: String): Offer {
        _terms = context.assets.open(filename).bufferedReader().use { it.readText() }
        return this
    }

    /**
     * Adds an item to the [uses] list.
     *
     * @param useCases
     * @param destinations
     * @return this Offer
     */
    fun use(useCases: List<Usecase>, destinations: List<String> = mutableListOf()): Offer {
        _uses.add(Use(useCases, destinations))
        return this
    }

    /**
     * Adds an item to the [tags] list.
     *
     * @param tag
     * @return this Offer
     */
    fun tag(tag: Tag): Offer {
        _tags.add(tag)
        return this
    }

    /**
     * Set the expiration of the [LicenseRecord]
     *
     * @param time
     * @param unit
     * @return this Offer
     */
    fun duration(time: Long, unit: TimeUnit): Offer {
        val expiryTime = System.currentTimeMillis() + unit.toMillis(time)
        _expiry = Date(expiryTime)
        return this
    }


    /**
     * Adds an item to the [permission] list.
     *
     * @param permission
     * @return
     */
    fun permission(permission: Permission): Offer {
        _permissions.add(permission)
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

