package com.mytiki.tiki_sdk_android

import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class TikiSdkTest {
    @Test
    fun initSdk() {
        val sdk : TikiSdk = TikiSdk( "")
        assertEquals(1,1)
    }
}