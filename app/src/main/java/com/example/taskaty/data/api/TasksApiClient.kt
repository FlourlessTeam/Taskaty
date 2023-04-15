package com.example.taskaty.data.api

import com.example.taskaty.BuildConfig
import com.example.taskaty.domain.entities.Task
import com.example.taskaty.domain.entities.TeamTask
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class TasksApiClient(private val okHttpClient: OkHttpClient) {
    private val baseUrl = BuildConfig.BASE_URL


    fun getPersonalTasks(callback: Callback) {
        val request = Request.Builder()
            .url("$baseUrl/todo/personal").get()
            .build()
        okHttpClient.newCall(request).enqueue(callback)
    }

    fun addPersonalTask(callback: Callback, task: Task) {
        val requestBody = task.toJson().toRequestBody("application/json".toMediaTypeOrNull())
        val request = Request.Builder().url("$baseUrl/todo/personal").post(requestBody).build()
        okHttpClient.newCall(request).enqueue(callback)

    }

    fun updatePersonalTask(callback: Callback, id: String, status: Int) {
        val taskData = stateDataToJson(id, status)
        val requestBody = taskData.toRequestBody("application/json".toMediaTypeOrNull())
        val request = Request.Builder().url("$baseUrl/todo/personal").put(requestBody).build()
        okHttpClient.newCall(request).enqueue(callback)

    }

    fun getTeamTasks(callback: Callback) {
        val request = Request.Builder()
            .url("$baseUrl/todo/team").get()
            .build()
        okHttpClient.newCall(request).enqueue(callback)


    }

    fun addTeamTask(callback: Callback, teamTask: TeamTask) {
        val requestBody = teamTask.toJson().toRequestBody("application/json".toMediaTypeOrNull())
        val request = Request.Builder().url("$baseUrl/todo/team").post(requestBody).build()
        okHttpClient.newCall(request).enqueue(callback)

    }

    fun updateTeamTask(callback: Callback, id: String, status: Int) {
        val taskData = stateDataToJson(id, status)
        val requestBody = taskData.toRequestBody("application/json".toMediaTypeOrNull())
        val request = Request.Builder().url("$baseUrl/todo/team").put(requestBody).build()
        okHttpClient.newCall(request).enqueue(callback)

    }

    private fun stateDataToJson(id: String, status: Int): String {
        val jsonObject = JSONObject()
        jsonObject.put("id", id)
        jsonObject.put("status", status)
        return jsonObject.toString()
    }

}
