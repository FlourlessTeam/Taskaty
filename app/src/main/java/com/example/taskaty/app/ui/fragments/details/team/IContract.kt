package com.example.taskaty.app.ui.fragments.details.team

import com.example.taskaty.domain.entities.TeamTask


interface IContract {
	interface IView {
		fun showMessage(message: String)
		fun updateUiData(task: TeamTask)
		fun showGetTeamTaskLoading()
		fun hideGetTeamTaskLoading()
		fun showUpdateTeamTaskLoading()
		fun hideUpdateTeamTaskLoading()
	}

	interface IPresenter {
		fun updateTeamTaskStatus(teamTaskId: String, status: Int)
		fun getTeamTask(teamTaskId: String)
	}
}