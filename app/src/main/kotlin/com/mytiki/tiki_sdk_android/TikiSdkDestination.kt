package com.mytiki.tiki_sdk_android

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
open class TikiSdkDestination(
    val paths: List<String>,
    val uses: List<String> = listOf("*"),
){
    object ALL : TikiSdkDestination(listOf("*"))
    object NONE : TikiSdkDestination(listOf(), listOf())
}
