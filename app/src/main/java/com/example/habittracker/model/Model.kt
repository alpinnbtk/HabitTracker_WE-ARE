package com.example.habittracker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Habit(
    @ColumnInfo(name="name")
    var name: String,
    @ColumnInfo(name="description")
    var description: String,
    @ColumnInfo(name="goal")
    var goal: Int,
    @ColumnInfo(name="unit")
    var unit: String,
    @ColumnInfo(name="progress")
    var progress: Int = 0,
    @ColumnInfo(name="icon")
    var icon: Int,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
)

@Entity
data class User(
    @ColumnInfo(name="username")
    var username: String,
    @ColumnInfo(name="password")
    var password: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
)