package com.example.taskaty.data.repositories.remote

import com.example.taskaty.data.api.TasksApiClient
import com.example.taskaty.data.api.interceptors.AuthInterceptor
import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.domain.entities.Task
import com.example.taskaty.domain.entities.TeamTask
import com.example.taskaty.domain.repositories.remote.TasksDataSource
import com.example.taskaty.domain.repositories.remote.TeamTasksDataSource
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import okio.IOException
import java.sql.Timestamp

class RemoteTasksRepository private constructor(private val userToken: String) : TasksDataSource,
    TeamTasksDataSource {

    private val okHttpClient by lazy {
        OkHttpClient.Builder().addInterceptor(AuthInterceptor(userToken)).build()
    }
    private val tasksApiClient by lazy { TasksApiClient(okHttpClient) }
    private var cachedPersonalTasks: List<Task> = listOf()
    private var cachedTeamTasks: List<TeamTask> = listOf()

    //
    override fun getAllPersonalTasks(callback: RepoCallback<List<Task>>) {

        if (cachedPersonalTasks.isEmpty())
            tasksApiClient.getPersonalTasks(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    callback.onError(RepoResponse.Error(e.toString()))
                }

                override fun onResponse(call: Call, response: Response) {
                    cachedPersonalTasks = jsonToTasks(response.body.toString())
                    callback.onSuccess(RepoResponse.Success(cachedPersonalTasks))
                }
            })
        else
            callback.onSuccess(RepoResponse.Success(cachedPersonalTasks))
    }

    override fun createPersonalTask(task: Task, callback: RepoCallback<Unit>) {
        tasksApiClient.addPersonalTask(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onError(RepoResponse.Error(e.toString()))
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onSuccess(RepoResponse.Success(Unit))
                //cachedPersonalTasks.add(task)
            }
        }, task)
    }

    override fun updatePersonalTaskState(
        taskId: String,
        status: Int,
        callback: RepoCallback<Unit>
    ) {
        tasksApiClient.updatePersonalTask(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onError(RepoResponse.Error(e.toString()))
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onSuccess(RepoResponse.Success(Unit))
            }
        }, taskId, status)
    }

    //
    override fun getAllTeamTasks(callback: RepoCallback<List<TeamTask>>) {
        if (cachedTeamTasks.isEmpty())
            tasksApiClient.getTeamTasks(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    callback.onError(RepoResponse.Error(e.toString()))
                }

                override fun onResponse(call: Call, response: Response) {
                    cachedTeamTasks = jsonToTeamTasks(response.body.toString())
                    callback.onSuccess(RepoResponse.Success(cachedTeamTasks))
                }
            })
        else
            callback.onSuccess(RepoResponse.Success(cachedTeamTasks))
    }

    override fun createTeamTask(teamTask: TeamTask, callback: RepoCallback<Unit>) {
        tasksApiClient.addTeamTask(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onError(RepoResponse.Error(e.toString()))
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onSuccess(RepoResponse.Success(Unit))
            }
        }, teamTask)
    }

    override fun updateTeamTaskState(taskId: String, status: Int, callback: RepoCallback<Unit>) {
        tasksApiClient.updateTeamTask(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onError(RepoResponse.Error(e.toString()))
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onSuccess(RepoResponse.Success(Unit))
            }
        }, taskId, status)
    }


    private fun jsonToTeamTask(json: JsonObject): TeamTask {
        return TeamTask(
            task = jsonToTask(json.toString()),
            assignee = json.get("assignee").asString
        )
    }
    private fun jsonToTeamTasks(jsonString: String): List<TeamTask> {
        val gson = Gson()
        val jsonObject = gson.fromJson(jsonString, JsonObject::class.java)
        val jsonTasks = jsonObject.getAsJsonArray("value")
        val tasks = mutableListOf<TeamTask>()
        for (jsonElement in jsonTasks) {
            val jsonTask = jsonElement.asJsonObject
            val task = jsonToTeamTask(jsonTask)
            tasks.add(task)
        }
        return tasks
    }
    private fun jsonToTask(json: String): Task {
        val gson = Gson()
        val taskJson = gson.fromJson(json, Task::class.java)

        return Task(
            taskJson.id,
            taskJson.title,
            taskJson.description,
            taskJson.status,
            Timestamp.valueOf(taskJson.creationTime.toString())
        )
    }
    private fun jsonToTasks(json: String): List<Task> {
        val gson = Gson()
        val tasksJson = gson.fromJson(json, JsonElement::class.java)
        val tasksArray = tasksJson.asJsonObject.getAsJsonArray("value")
        val tasksList = mutableListOf<Task>()

        tasksArray.forEach { taskJson ->
            tasksList.add(jsonToTask(taskJson.toString()))
        }

        return tasksList
    }

    companion object {
        private var instance: RemoteTasksRepository? = null

        fun getInstance(userToken: String): RemoteTasksRepository {
            if (instance == null) {
                instance = RemoteTasksRepository(userToken)
            }
            return instance!!
        }
    }
}
