/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.tiki_sdk_android.idp

import com.mytiki.tiki_sdk_android.channel.Channel
import com.mytiki.tiki_sdk_android.channel.req.ReqDefault
import com.mytiki.tiki_sdk_android.channel.rsp.RspDefault
import com.mytiki.tiki_sdk_android.idp.req.ReqExport
import com.mytiki.tiki_sdk_android.idp.req.ReqImport
import com.mytiki.tiki_sdk_android.idp.req.ReqKey
import com.mytiki.tiki_sdk_android.idp.req.ReqSign
import com.mytiki.tiki_sdk_android.idp.req.ReqVerify
import com.mytiki.tiki_sdk_android.idp.rsp.RspExport
import com.mytiki.tiki_sdk_android.idp.rsp.RspSign
import com.mytiki.tiki_sdk_android.idp.rsp.RspToken
import com.mytiki.tiki_sdk_android.idp.rsp.RspVerify
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async

class Idp(private val channel: Channel) {

    fun export(keyId: String, public: Boolean = false): Deferred<String?> {
        return MainScope().async {
            channel.request(IdpMethod.EXPORT, ReqExport(keyId, public)) { args ->
                RspExport.from(args)
            }.await().key
        }
    }

    fun import(keyId: String, key: ByteArray, public: Boolean = false): Deferred<Unit> {
        return MainScope().async {
            channel.request(
                IdpMethod.IMPORT,
                ReqImport(keyId, key, public)
            ) { args -> RspDefault.from(args) }.await()
            Unit
        }
    }

    fun key(keyId: String, overwrite: Boolean = false): Deferred<Unit> {
        return MainScope().async {
            channel.request(IdpMethod.KEY, ReqKey(keyId, overwrite)) { args ->
                RspDefault.from(args)
            }.await()
            Unit
        }
    }

    fun sign(keyId: String, message: ByteArray): Deferred<ByteArray?> {
        return MainScope().async {
            channel.request(IdpMethod.SIGN, ReqSign(keyId, message)) { args ->
                RspSign.from(args)
            }.await().signature
        }
    }

    fun verify(keyId: String, message: ByteArray, signature: ByteArray): Deferred<Boolean> {
        return MainScope().async {
            channel.request(IdpMethod.VERIFY, ReqVerify(keyId, message, signature)) { args ->
                RspVerify.from(args)
            }.await().isVerified
        }
    }

    fun token(): Deferred<Token> {
        return MainScope().async {
            val rsp: RspToken = channel.request(IdpMethod.TOKEN, ReqDefault()) { args ->
                RspToken.from(args)
            }.await()
            Token.from(rsp)
        }
    }
}