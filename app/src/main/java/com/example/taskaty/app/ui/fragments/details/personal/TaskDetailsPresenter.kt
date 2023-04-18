package com.example.taskaty.app.ui.fragments.details.personal

import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.domain.entities.Task
import com.example.taskaty.domain.interactors.PersonalTaskInteractor

class TaskDetailsPresenter(
	private val view: IContract.IView,
	private val personalTaskInteractor: PersonalTaskInteractor
) : IContract.IPresenter {

	override fun getTask(taskId: String) {
		view.showGetTaskLoading()
		personalTaskInteractor.getTaskById(taskId, object : RepoCallback<Task> {
			override fun onSuccess(response: RepoResponse.Success<Task>) {
				view.hideGetTaskLoading()
				view.updateUiData(response.data)
			}

			override fun onError(response: RepoResponse.Error<Task>) {
				view.showMessage(response.message)
			}
		})

	}

	override fun updateTaskStatus(taskId: String, status: Int) {
		view.showUpdateTaskLoading()
		personalTaskInteractor.updateTaskStatus(taskId, status, object : RepoCallback<Unit> {
			override fun onSuccess(response: RepoResponse.Success<Unit>) {
				view.hideUpdateTaskLoading()
				view.showMessage("Task Updated Successfully")
			}

			override fun onError(response: RepoResponse.Error<Unit>) {
				view.hideUpdateTaskLoading()
				view.showMessage("Update Task Failed")
			}
		})

	}

}