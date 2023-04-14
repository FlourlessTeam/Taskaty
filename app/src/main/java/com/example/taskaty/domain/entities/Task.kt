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
    constructor(jsonObject: JSONObject) : this(
        id = jsonObject.getInt("id"),
        title = jsonObject.getString("title"),
        description = jsonObject.getString("description"),
        status = jsonObject.getInt("status"),
        creationTime = Timestamp(jsonObject.getLong("creationTime"))
    )
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
