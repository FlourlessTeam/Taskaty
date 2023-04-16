package com.example.taskaty.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.json.JSONObject

@Parcelize
data class Task(
	val id: String,
	val title: String,
	val description: String,
	var status: Int,
	val creationTime: String
) :
	Parcelable {

	fun toJson(): String {
		val jsonObject = JSONObject()
		jsonObject.put("id", id)
		jsonObject.put("title", title)
		jsonObject.put("description", description)
		jsonObject.put("status", status)
		jsonObject.put("creationTime", creationTime)
		return jsonObject.toString()
	}


}
