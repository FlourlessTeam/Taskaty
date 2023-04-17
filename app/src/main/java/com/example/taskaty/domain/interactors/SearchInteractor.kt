package com.example.taskaty.domain.interactors

import com.example.taskaty.data.repositories.remote.RemoteTasksRepository
import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.domain.entities.Task
import com.example.taskaty.domain.entities.TeamTask

class SearchInteractor(private val repo: RemoteTasksRepository) {

    private fun searchTasks(title: String, callback: RepoCallback<List<Task>>) {
        repo.getAllPersonalTasks(object : RepoCallback<List<Task>> {
            override fun onSuccess(response: RepoResponse.Success<List<Task>>) {
                val taskSearch = response.data.filter { it.title.contains(title) }
                callback.onSuccess(RepoResponse.Success(taskSearch))
            }

            override fun onError(response: RepoResponse.Error<List<Task>>) {
                callback.onError(RepoResponse.Error(response.message))
            }
        })
    }

    private fun searchTeamTasks(title: String, callback: RepoCallback<List<TeamTask>>) {
        repo.getAllTeamTasks(object : RepoCallback<List<TeamTask>> {
            override fun onSuccess(response: RepoResponse.Success<List<TeamTask>>) {
                val teamSearchTasks = response.data.filter { it.task.title.contains(title) }
                callback.onSuccess(RepoResponse.Success(teamSearchTasks))
            }

            override fun onError(response: RepoResponse.Error<List<TeamTask>>) {
                callback.onError(RepoResponse.Error(response.message))
            }
        })
    }

    fun searchAllTasks(callback: RepoCallback<Pair<List<Task>, List<TeamTask>>>, title: String) {
        var fetchedTask: List<Task>? = null
        var fetchedTeamTask: List<TeamTask>? = null
        searchTasks(title, object : RepoCallback<List<Task>> {
            override fun onSuccess(response: RepoResponse.Success<List<Task>>) {
                fetchedTask = response.data
                if (fetchedTask != null || fetchedTeamTask != null) {
                    callback.onSuccess(RepoResponse.Success(Pair(fetchedTask!!, fetchedTeamTask!!)))
                }

            }
            override fun onError(response: RepoResponse.Error<List<Task>>) {
                callback.onError(RepoResponse.Error(response.message))
            }
        })
        searchTeamTasks(title, object : RepoCallback<List<TeamTask>> {
            override fun onSuccess(response: RepoResponse.Success<List<TeamTask>>) {
                fetchedTeamTask = response.data
                if (fetchedTask != null || fetchedTeamTask != null) {
                    callback.onSuccess(RepoResponse.Success(Pair(fetchedTask!!, fetchedTeamTask!!)))
                }
            }

            override fun onError(response: RepoResponse.Error<List<TeamTask>>) {
                callback.onError(RepoResponse.Error(response.message))
            }
        })

    }
}