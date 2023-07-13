/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.trail

import com.mytiki.tiki_sdk_android.channel.Channel
import com.mytiki.tiki_sdk_android.trail.req.ReqTitle
import com.mytiki.tiki_sdk_android.trail.req.ReqTitleGet
import com.mytiki.tiki_sdk_android.trail.req.ReqTitleId
import com.mytiki.tiki_sdk_android.trail.rsp.RspTitle
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async

class Title(private val channel: Channel) {
    
    fun create(
        ptr: String,
        tags: List<Tag>,
        description: String? = null,
        origin: String? = null
    ): Deferred<TitleRecord> {
        return MainScope().async {
            val rsp: RspTitle = channel.request(
                TrailMethod.TITLE_CREATE,
                ReqTitle(ptr, tags, description, origin)
            ) { args ->
                RspTitle.from(args)
            }.await()
            TitleRecord.from(rsp)
        }
    }

    fun get(ptr: String, origin: String? = null): Deferred<TitleRecord> {
        return MainScope().async {
            val rsp: RspTitle = channel.request(
                TrailMethod.TITLE_GET,
                ReqTitleGet(ptr, origin)
            ) { args ->
                RspTitle.from(args)
            }.await()
            TitleRecord.from(rsp)
        }
    }

    fun id(id: String): Deferred<TitleRecord> {
        return MainScope().async {
            val rsp: RspTitle = channel.request(
                TrailMethod.TITLE_ID,
                ReqTitleId(id)
            ) { args ->
                RspTitle.from(args)
            }.await()
            TitleRecord.from(rsp)
        }
    }
}