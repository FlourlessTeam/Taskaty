package com.example.taskaty.domain.repositories.remote

import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.domain.entities.PersonalTask

interface TasksDataSource {
    fun getAllPersonalTasks(callback: RepoCallback<List<PersonalTask>>)
    fun createPersonalTask(task: PersonalTask, callback: RepoCallback<Unit>)
    fun updatePersonalTaskState(taskId: String, status: Int, callback: RepoCallback<Unit>)
}
