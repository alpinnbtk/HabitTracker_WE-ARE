package com.example.habittracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.habittracker.model.Habit
import com.example.habittracker.model.AppDatabase
import com.example.habittracker.util.FileHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.ArraySerializer
import kotlin.compareTo
import kotlin.coroutines.CoroutineContext
import kotlin.dec
import kotlin.inc
import kotlin.text.compareTo

class HabitViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext = job + Dispatchers.IO
    private val db = AppDatabase.buildDatabase(application)
    private val habitDao = db.habitDao()
    val habits = MutableLiveData<ArrayList<Habit>>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Boolean>()


    private val habitList = arrayListOf<Habit>()

    fun loadHabits() {
        loading.postValue(true)
        error.postValue(false)


        launch {
            try {
                val list = habitDao.selectAllHabit()
                habits.postValue(ArrayList(list))
            }catch(e : Exception) {
                error.postValue(true)
            }
        }


        loading.postValue(false)
    }

    fun addHabit(habit: Habit) {


        launch{
            habitDao.insertHabit(habit)
            loadHabits()
        }

    }

    fun tambahProgress(habit : Habit) {
        launch{
            if (habit.progress < habit.goal)
            {
                habitDao.updateProgress(habit.id, habit.progress - 1)
                loadHabits()
            }
        }
    }

    fun kurangProgress(habit : Habit) {
         launch{
            if (habit.progress > 0)
            {
                habitDao.updateProgress(habit.id, habit.progress + 1)
                loadHabits()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        job.cancel()
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