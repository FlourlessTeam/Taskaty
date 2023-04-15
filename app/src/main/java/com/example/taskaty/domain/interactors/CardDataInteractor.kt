package com.example.taskaty.domain.interactors

import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.domain.entities.Task
import com.example.taskaty.domain.entities.TeamTask
import com.example.taskaty.domain.repositories.remote.TasksDataSource
import com.example.taskaty.domain.repositories.remote.TeamTasksDataSource

class CardDataInteractor(
    private val tasksDataSource: TasksDataSource,
    private val teamTasksDataSource: TeamTasksDataSource,
) {

    fun getPersonalTasksData(statusType: Int, callback: RepoCallback<List<Task>>) {
        tasksDataSource.getAllPersonalTasks(object : RepoCallback<List<Task>> {

            override fun onSuccess(response: RepoResponse.Success<List<Task>>) {

                val task = response.data.filter { it.status == statusType }

                callback.onSuccess(RepoResponse.Success(task))
            }

            override fun onError(response: RepoResponse.Error<List<Task>>) {

                callback.onError(RepoResponse.Error(response.message))
            }
        })
    }

    fun getTeamTasksData(statusType: Int, callback: RepoCallback<List<TeamTask>>) {
        teamTasksDataSource.getAllTeamTasks(object : RepoCallback<List<TeamTask>> {

            override fun onSuccess(response: RepoResponse.Success<List<TeamTask>>) {

                val teamTask = response.data.filter { it.task.status == statusType }

                callback.onSuccess(RepoResponse.Success(teamTask))
            }
            override fun onError(response: RepoResponse.Error<List<TeamTask>>) {

                callback.onError(RepoResponse.Error(response.message))

            }
        })
    }
}