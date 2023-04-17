package com.example.taskaty.app.ui.fragments.home.bottomSheet

import com.example.taskaty.data.repositories.remote.RemoteTasksRepository
import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.domain.entities.Task
import com.example.taskaty.domain.entities.TeamTask
import com.example.taskaty.domain.repositories.remote.TasksDataSource
import com.example.taskaty.domain.repositories.remote.TeamTasksDataSource

class NewTaskPresenter {
    private val token =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwczovL3RoZS1jaGFuY2Uub3JnLyIsInN1YiI6IjIyMjg4N2RiLWExNDAtNDZhZi1hNTc2LWM5NDhjN2E3NjhkMyIsInRlYW1JZCI6ImMyYzAyNTA3LTk5NjgtNDg2Yi05YmYwLTRjMzg2MGZlMWYyZCIsImlzcyI6Imh0dHBzOi8vdGhlLWNoYW5jZS5vcmcvIiwiZXhwIjoxNjgxNjczMDQ0fQ.pDGoQvavAJVrorD6RiOX-09pYq2_qnxisLz30CrgY-k"
    private val personalTaskDataSource: TasksDataSource = RemoteTasksRepository.getInstance(token)
    private val teamTaskDataSource: TeamTasksDataSource = RemoteTasksRepository.getInstance(token)


    fun onCreateBtnClicked(
        title: String,
        description: String,
        assignee: String,
        selectedTabPosition: Int,
        repo: RepoCallback<Unit>
    ) {
        //selectedTabPosition when equal 0 means we in personal task fragment
        //selectedTabPosition when equal 1 means we in team task fragment
        val task = Task(
            id = "",
            title = title,
            description = description,
            status = 0,
            creationTime = ""
        )
        if (selectedTabPosition == 0) {
            personalTaskDataSource.createPersonalTask(task, repo)
        } else {
            val teamTask = TeamTask(task, assignee)
            teamTaskDataSource.createTeamTask(teamTask, repo)
        }
    }
}