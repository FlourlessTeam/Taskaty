package com.example.taskaty.domain.repositories

import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.domain.entities.Task
import okhttp3.Callback

interface TasksDataSource {
    fun getAllPersonalTasks(callback: RepoCallback<List<Task>>)
    fun createPersonalTask(task: Task, callback: RepoCallback<Unit>)
    fun updatePersonalTaskState(taskId: String, status: Int, callback: RepoCallback<Unit>)
}
