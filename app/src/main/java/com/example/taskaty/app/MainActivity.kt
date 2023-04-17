package com.example.taskaty.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.taskaty.app.ui.fragments.OnBoardingFragment
import com.example.taskaty.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().add(binding.containerFragment.id, OnBoardingFragment()).commit()
    }

}