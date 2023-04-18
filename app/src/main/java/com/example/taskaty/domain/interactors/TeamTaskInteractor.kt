package com.example.taskaty.domain.interactors

import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.domain.entities.TeamTask
import com.example.taskaty.domain.repositories.remote.TeamTasksDataSource

class TeamTaskInteractor(
	private val teamTasksDataSource: TeamTasksDataSource,
) {
	fun getTeamTaskData( callback: RepoCallback<List<TeamTask>>) {
		teamTasksDataSource.getAllTeamTasks(object : RepoCallback<List<TeamTask>> {
			override fun onSuccess(response: RepoResponse.Success<List<TeamTask>>) {
				val teamTask = response.data
				callback.onSuccess(RepoResponse.Success(teamTask))
			}

			override fun onError(response: RepoResponse.Error<List<TeamTask>>) {
				val message = response.message
				callback.onError(RepoResponse.Error(message))
			}
		})
	}


	fun updateTeamTaskStatus(
		teamTaskId: String,
		teamTaskStatus: Int,
		callback: RepoCallback<Unit>
	) {
		teamTasksDataSource.updateTeamTaskState(
			teamTaskId,
			teamTaskStatus,
			object : RepoCallback<Unit> {
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