package com.example.taskaty.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.json.JSONObject
import java.sql.Timestamp

@Parcelize
data class Task(
    val id: Int,
    val title: String,
    val description: String,
    var status: Int,
    val creationTime: Timestamp
) :
    Parcelable {

    fun toJson(): String {
        val jsonObject = JSONObject()
        jsonObject.put("id", id)
        jsonObject.put("title", title)
        jsonObject.put("description", description)
        jsonObject.put("status", status)
        jsonObject.put("creationTime", creationTime.time)
        return jsonObject.toString()
    }



}
