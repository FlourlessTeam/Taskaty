package com.example.taskaty.data.api

import org.json.JSONObject

data class LoginResponse(
    val isSuccess: Boolean,
    val message: String,
    val token: String?
) {
    constructor(jsonObject: JSONObject) : this(
        isSuccess = jsonObject.getBoolean("isSuccess"),
        message = jsonObject.getString("message"),
        token = jsonObject.getJSONObject("value").getString("token")
    )
}