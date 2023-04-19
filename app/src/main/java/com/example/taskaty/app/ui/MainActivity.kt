package com.example.taskaty.app.ui

import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.taskaty.app.ui.fragments.onBoarding.OnBoardingFragment
import com.example.taskaty.app.ui.fragments.viewAll.personal.ViewAllPersonalTasksFragment
import com.example.taskaty.app.ui.fragments.viewAll.team.ViewAllTeamTasksFragment
import com.example.taskaty.databinding.ActivityMainBinding
import com.example.taskaty.global.GlobalState

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupAppContext()
        supportFragmentManager.beginTransaction()
         .add(binding.containerFragment.id, ViewAllPersonalTasksFragment()).commit()
    }

    private fun setupAppContext() {
        GlobalState.appContext = applicationContext
    }

}