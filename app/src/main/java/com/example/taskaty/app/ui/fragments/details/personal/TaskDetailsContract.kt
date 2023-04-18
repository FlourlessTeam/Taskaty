package com.example.taskaty.app.ui.fragments.details.personal

import com.example.taskaty.domain.entities.Task


interface TaskDetailsContract {
	interface IView {
		fun showMessage(message: String)
		fun updateUiData(task: Task)
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