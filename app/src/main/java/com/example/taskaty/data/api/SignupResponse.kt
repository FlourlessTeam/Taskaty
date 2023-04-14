package com.example.taskaty.data.api

import org.json.JSONObject


data class SignupResponse(
    val isSuccess: Boolean,
    val message: String
) {
    constructor(jsonObject: JSONObject) : this(
        isSuccess = jsonObject.getBoolean("isSuccess"),
        message = jsonObject.getString("message")
    )
}
