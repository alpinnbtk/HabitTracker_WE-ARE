package com.example.habittracker.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.habittracker.R
import com.example.habittracker.databinding.ActivityMainBinding
import com.example.habittracker.util.SessionManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController

        val navGraph = navController.navInflater.inflate(R.navigation.habit_navigation)

        val sessionManager = SessionManager(this)
        navGraph.setStartDestination(
            if (sessionManager.isLoggedIn()) R.id.dashboardFragment
            else R.id.loginFragment
        )

        navController.graph = navGraph
    }
}