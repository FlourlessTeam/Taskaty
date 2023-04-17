package com.example.taskaty.domain.interactors

import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.domain.entities.Task
import com.example.taskaty.domain.repositories.remote.TasksDataSource

class PersonalTaskInteractor(
	private val tasksDataSource: TasksDataSource,
) {
	fun getTaskById(taskId: String, callback: RepoCallback<Task>) {
		tasksDataSource.getAllPersonalTasks(object : RepoCallback<List<Task>> {
			override fun onSuccess(response: RepoResponse.Success<List<Task>>) {
				val task = response.data.find { it.id == taskId }!!
				callback.onSuccess(RepoResponse.Success(task))
			}

			override fun onError(response: RepoResponse.Error<List<Task>>) {
				val message = response.message
				callback.onError(RepoResponse.Error(message))
			}
		})
	}

	fun updateTaskStatus(taskId: String, taskStatus: Int, callback: RepoCallback<Unit>) {
		tasksDataSource.updatePersonalTaskState(taskId, taskStatus, object : RepoCallback<Unit> {
			override fun onSuccess(response: RepoResponse.Success<Unit>) {
				callback.onSuccess(RepoResponse.Success(Unit))
			}

			override fun onError(response: RepoResponse.Error<Unit>) {
				val message = response.message
				callback.onError(RepoResponse.Error(message))
			}
		})
	}
}