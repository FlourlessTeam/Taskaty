package com.example.taskaty.domain.repositories.remote

import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.domain.entities.TeamTask

interface TeamTasksDataSource{
    fun getAllTeamTasks(callback: RepoCallback<List<TeamTask>>)
    fun createTeamTask(task: TeamTask, callback: RepoCallback<Unit>)
    fun updateTeamTaskState(taskId: String, status: Int, callback: RepoCallback<Unit>)
}