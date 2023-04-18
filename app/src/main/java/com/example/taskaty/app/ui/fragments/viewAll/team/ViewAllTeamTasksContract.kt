package com.example.taskaty.app.ui.fragments.viewAll.team

import com.example.taskaty.domain.entities.TeamTask


interface ViewAllTeamTasksContract {
    interface View {
        fun viewAllTeamTasksStatus(teamTasks: List<TeamTask>)
    }

    interface Presenter {
        fun getTeamTaskData()
    }
}