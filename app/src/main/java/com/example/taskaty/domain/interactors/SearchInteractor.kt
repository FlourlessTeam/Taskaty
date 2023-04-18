package com.example.taskaty.domain.interactors

import com.example.taskaty.data.repositories.remote.RemoteTasksRepository
import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.domain.entities.PersonalTask
import com.example.taskaty.domain.entities.TeamTask

class SearchInteractor(private val repo: RemoteTasksRepository) {

    fun searchTasks( title: String, callback: RepoCallback<List<PersonalTask>>) {
        repo.getAllPersonalTasks(object : RepoCallback<List<PersonalTask>> {
            override fun onSuccess(response: RepoResponse.Success<List<PersonalTask>>) {
                val taskSearch = response.data.filter { it.title.contains(title) }
                callback.onSuccess(RepoResponse.Success(taskSearch))
            }

            override fun onError(response: RepoResponse.Error<List<PersonalTask>>) {
                callback.onError(RepoResponse.Error(response.message))
            }
        })
    }

    fun searchTeamTasks( title: String, callback: RepoCallback<List<TeamTask>>) {
        repo.getAllTeamTasks(object : RepoCallback<List<TeamTask>> {
            override fun onSuccess(response: RepoResponse.Success<List<TeamTask>>) {
                val teamSearchTasks = response.data.filter { it.title.contains(title) }
                callback.onSuccess(RepoResponse.Success(teamSearchTasks))
            }

            override fun onError(response: RepoResponse.Error<List<TeamTask>>) {
                callback.onError(RepoResponse.Error(response.message))
            }
        })
    }
}