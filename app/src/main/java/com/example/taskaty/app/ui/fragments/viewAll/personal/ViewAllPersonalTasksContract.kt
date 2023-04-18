package com.example.taskaty.app.ui.fragments.viewAll.personal

import com.example.taskaty.domain.entities.Task

interface ViewAllPersonalTasksContract {
    interface View {
        fun viewAllPersonalTasksStatus(tasks: List<Task>)
    }

    interface Presenter {
        fun getPersonalTasks()
    }
}