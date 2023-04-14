package com.example.taskaty.domain.repositories

import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.domain.entities.Task
import com.example.taskaty.domain.entities.TeamTask
import okhttp3.Callback

interface TeamTasksDataSource{
    fun getAllTeamTasks(callback: RepoCallback<List<TeamTask>>)
    fun createTeamTask(teamTask: TeamTask, callback: RepoCallback<Unit>)
    fun updateTeamTaskState(taskId: String, status: Int, callback: RepoCallback<Unit>)
}