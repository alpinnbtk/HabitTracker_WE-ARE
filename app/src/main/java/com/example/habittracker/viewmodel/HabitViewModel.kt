package com.example.habittracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.habittracker.model.Habit
import com.example.habittracker.util.FileHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.compareTo
import kotlin.dec
import kotlin.inc
import kotlin.text.compareTo

class HabitViewModel(application: Application) : AndroidViewModel(application) {
    val habits = MutableLiveData<ArrayList<Habit>>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Boolean>()

    private val habitList = arrayListOf<Habit>()

    fun loadHabits() {
        loading.value = true
        error.value = false

        habits.value = habitList

        loading.value = false
    }

    fun addHabit(habit: Habit) {
        habitList.add(habit)
        habits.value = ArrayList(habitList)
        saveToFile()
    }

    fun tambahProgress(position: Int) {
        val habit = habitList[position]
        if (habit.progress < habit.goal) {
            habit.progress++
            habits.value = ArrayList(habitList)
            saveToFile()
        }
    }

    fun kurangProgress(position: Int) {
        val habit = habitList[position]
        if (habit.progress > 0) {
            habit.progress--
            habits.value = ArrayList(habitList)
            saveToFile()
        }
    }

    fun loadFromFile() {
        val helper = FileHelper(getApplication())
        val json = helper.readFromFile()

        if (json.isNotEmpty()) {
            val type = object : TypeToken<ArrayList<Habit>>() {}.type
            val list: ArrayList<Habit> = Gson().fromJson(json, type)

            habitList.clear()
            habitList.addAll(list)

            habits.value = ArrayList(habitList)
        }
    }

    fun saveToFile() {
        val helper = FileHelper(getApplication())
        val json = Gson().toJson(habitList)
        helper.writeToFile(json)
    }
}