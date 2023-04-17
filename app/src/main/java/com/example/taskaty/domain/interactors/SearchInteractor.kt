package com.example.taskaty.domain.interactors

import com.example.taskaty.data.repositories.remote.RemoteTasksRepository
import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.domain.entities.Task
import com.example.taskaty.domain.entities.TeamTask

class SearchInteractor(private val repo: RemoteTasksRepository) {

    fun searchTasks(status: Int, title: String, callback: RepoCallback<List<Task>>) {
        repo.getAllPersonalTasks(object : RepoCallback<List<Task>> {
            override fun onSuccess(response: RepoResponse.Success<List<Task>>) {
                val taskSearch = response.data.filter { it.title.contains(title) && it.status == status }
                callback.onSuccess(RepoResponse.Success(taskSearch))
            }

            override fun onError(response: RepoResponse.Error<List<Task>>) {
                callback.onError(RepoResponse.Error(response.message))
            }
        })
    }

    fun searchTeamTasks(status: Int, title: String, callback: RepoCallback<List<TeamTask>>) {
        repo.getAllTeamTasks(object : RepoCallback<List<TeamTask>> {
            override fun onSuccess(response: RepoResponse.Success<List<TeamTask>>) {
                val teamSearchTasks = response.data.filter { it.task.title.contains(title) && it.task.status == status }
                callback.onSuccess(RepoResponse.Success(teamSearchTasks))
            }

            override fun onError(response: RepoResponse.Error<List<TeamTask>>) {
                callback.onError(RepoResponse.Error(response.message))
            }
        })
    }
}