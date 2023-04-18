package com.example.taskaty.data.repositories.remote

import com.example.taskaty.data.api.TasksApiClient
import com.example.taskaty.data.api.interceptors.AuthInterceptor
import com.example.taskaty.data.mappers.TaskMappers
import com.example.taskaty.data.repositories.local.LocalAuthRepository
import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.domain.entities.PersonalTask
import com.example.taskaty.domain.entities.TeamTask
import com.example.taskaty.domain.repositories.local.LocalAuthDataSource
import com.example.taskaty.domain.repositories.remote.TasksDataSource
import com.example.taskaty.domain.repositories.remote.TeamTasksDataSource
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import okio.IOException

class RemoteTasksRepository private constructor() : TasksDataSource,
    TeamTasksDataSource {

    private val localAuthRepo: LocalAuthDataSource by lazy { LocalAuthRepository.getInstance() }
    private val userToken by lazy { localAuthRepo.getToken() }
    private val okHttpClient by lazy {
        OkHttpClient.Builder().addInterceptor(AuthInterceptor(userToken)).build()
    }
    private val tasksApiClient by lazy { TasksApiClient(okHttpClient) }
    private var cachedPersonalTasks: List<PersonalTask> = listOf()
    private var cachedTeamTasks: List<TeamTask> = listOf()

    //
    override fun getAllPersonalTasks(callback: RepoCallback<List<PersonalTask>>) {

        if (cachedPersonalTasks.isEmpty())
            tasksApiClient.getPersonalTasks(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    callback.onError(RepoResponse.Error(e.toString()))
                }

                override fun onResponse(call: Call, response: Response) {
                    cachedPersonalTasks = TaskMappers.jsonToTasks(response.body.string())
                    callback.onSuccess(RepoResponse.Success(cachedPersonalTasks))
                }
            })
        else
            callback.onSuccess(RepoResponse.Success(cachedPersonalTasks))
    }

    override fun createPersonalTask(task: PersonalTask, callback: RepoCallback<Unit>) {
        tasksApiClient.addPersonalTask(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onError(RepoResponse.Error(e.toString()))
            }

            override fun onResponse(call: Call, response: Response) {
                cachedPersonalTasks = cachedPersonalTasks + listOf(task)
                callback.onSuccess(RepoResponse.Success(Unit))
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
                    cachedTeamTasks = TaskMappers.jsonToTeamTasks(response.body.string())
                    callback.onSuccess(RepoResponse.Success(cachedTeamTasks))
                }
            })
        else
            callback.onSuccess(RepoResponse.Success(cachedTeamTasks))
    }

    override fun createTeamTask(task: TeamTask, callback: RepoCallback<Unit>) {
        tasksApiClient.addTeamTask(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onError(RepoResponse.Error(e.toString()))
            }

            override fun onResponse(call: Call, response: Response) {
                cachedTeamTasks = cachedTeamTasks + listOf(task)
                callback.onSuccess(RepoResponse.Success(Unit))
            }
        }, task)
    }

    override fun updateTeamTaskState(
        taskId: String,
        status: Int,
        callback: RepoCallback<Unit>
    ) {
        tasksApiClient.updateTeamTask(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onError(RepoResponse.Error(e.toString()))
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onSuccess(RepoResponse.Success(Unit))
            }
        }, taskId, status)
    }

    companion object {
        private var instance: RemoteTasksRepository? = null

        fun getInstance(): RemoteTasksRepository {
            if (instance == null) {
                instance = RemoteTasksRepository()
            }
            return instance!!
        }
    }
}
