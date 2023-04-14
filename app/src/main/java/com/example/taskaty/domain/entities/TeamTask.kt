package com.example.taskaty.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.json.JSONObject

@Parcelize
data class TeamTask(val task: Task, val assignee: String) : Parcelable {
    constructor(jsonObject: JSONObject) : this(
        task = Task(jsonObject),
        assignee = jsonObject.getString("assignee"),
    )

    fun toJson(): String {
        val jsonObject = JSONObject()
        jsonObject.put("task", task.toJson())
        jsonObject.put("assignee", assignee)
        return jsonObject.toString()
    }
}
