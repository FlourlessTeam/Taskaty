package com.example.taskaty.app.ui.fragments.home.bottomSheet

import com.example.taskaty.data.repositories.remote.RemoteTasksRepository
import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.domain.entities.PersonalTask
import com.example.taskaty.domain.entities.TeamTask
import com.example.taskaty.domain.repositories.remote.TasksDataSource
import com.example.taskaty.domain.repositories.remote.TeamTasksDataSource

class NewTaskPresenter {

    private val personalTaskDataSource: TasksDataSource = RemoteTasksRepository.getInstance()
    private val teamTaskDataSource: TeamTasksDataSource = RemoteTasksRepository.getInstance()


    fun onCreateBtnClicked(
        title: String,
        description: String,
        assignee: String,
        selectedTabPosition: Int,
        repo: RepoCallback<Unit>
    ) {
        //selectedTabPosition when equal 0 means we in personal task fragment
        //selectedTabPosition when equal 1 means we in team task fragment
        val task = TeamTask(
            id = "",
            title = title,
            description = description,
            status = 0,
            creationTime = "",
            assignee = assignee
        )
        val personalTask = PersonalTask(
            id = "",
            title = title,
            description = description,
            status = 0,
            creationTime = ""
        )
        if (selectedTabPosition == 0) {
            personalTaskDataSource.createPersonalTask(personalTask, repo)
        } else {

            teamTaskDataSource.createTeamTask(task, repo)
        }
    }
}