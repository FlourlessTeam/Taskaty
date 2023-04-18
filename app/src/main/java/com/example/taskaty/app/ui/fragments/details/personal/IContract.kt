package com.example.taskaty.app.ui.fragments.details.personal

import com.example.taskaty.domain.entities.PersonalTask


interface IContract {
	interface IView {
		fun showMessage(message: String)
		fun updateUiData(task: PersonalTask)
		fun showGetTaskLoading()
		fun hideGetTaskLoading()
		fun showUpdateTaskLoading()
		fun hideUpdateTaskLoading()
	}

	interface IPresenter {
		fun updateTaskStatus(taskId: String, status: Int)
		fun getTask(taskId: String)
	}
}