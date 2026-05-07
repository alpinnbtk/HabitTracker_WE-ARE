package com.example.habittracker.model

data class Habit(
    var name: String,
    var description: String,
    var goal: Int,
    var unit: String,
    var progress: Int = 0,
    var icon: Int
)

data class User(
    var username: String,
    var password: String
)