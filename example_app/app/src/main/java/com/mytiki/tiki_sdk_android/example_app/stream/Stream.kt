package com.mytiki.tiki_sdk_android.example_app.stream

data class Stream(
    val source: String,
    var body: String = "{\"message\" : \"Hello Tiki!\"}",
    var httpMethod: String = "POST",
    var interval: Int = 15,
    var url: String = "https://postman-echo.com/post"
)