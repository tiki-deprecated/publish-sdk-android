package com.mytiki.tiki_sdk_android.example_app.destination

data class Destination(
    val source: String,
    var body: String = "{\"message\" : \"Hello Tiki!\"}",
    var httpMethod: String = "POST",
    var interval: Int = 15,
    var url: String = "https://postman-echo.com/post"
)