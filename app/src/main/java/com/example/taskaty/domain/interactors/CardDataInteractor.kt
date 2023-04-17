package com.example.taskaty.domain.interactors

import com.example.taskaty.data.repositories.remote.RemoteTasksRepository
import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.domain.entities.Task
import com.example.taskaty.domain.entities.TeamTask

class CardDataInteractor(private val repo: RemoteTasksRepository) {

    fun getPersonalTasksData(callback: RepoCallback<List<Task>>) {
        repo.getAllPersonalTasks(object : RepoCallback<List<Task>> {
            override fun onSuccess(response: RepoResponse.Success<List<Task>>) {
                val task = response.data
                callback.onSuccess(RepoResponse.Success(task))
            }

            override fun onError(response: RepoResponse.Error<List<Task>>) {
                callback.onError(RepoResponse.Error(response.message))
            }
        })
    }

    fun getTeamTasksData(callback: RepoCallback<List<TeamTask>>) {
        repo.getAllTeamTasks(object : RepoCallback<List<TeamTask>> {
            override fun onSuccess(response: RepoResponse.Success<List<TeamTask>>) {
                val teamTask = response.data
                callback.onSuccess(RepoResponse.Success(teamTask))
            }

            override fun onError(response: RepoResponse.Error<List<TeamTask>>) {
                callback.onError(RepoResponse.Error(response.message))
            }
        })
    }
}