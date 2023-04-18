package com.example.taskaty.domain.interactors

import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.domain.entities.Task
import com.example.taskaty.domain.repositories.remote.TasksDataSource

class SearchInteractor(private val repo: TasksDataSource) {

   fun searchTasks(title: String, callback: RepoCallback<List<Task>>) {
        repo.getAllPersonalTasks(object : RepoCallback<List<Task>> {
            override fun onSuccess(response: RepoResponse.Success<List<Task>>) {
                val taskSearch = response.data.filter { it.title.contains(title)}
                callback.onSuccess(RepoResponse.Success(taskSearch))
            }

            override fun onError(response: RepoResponse.Error<List<Task>>) {
                callback.onError(RepoResponse.Error(response.message))
            }
        })
    }


    }
