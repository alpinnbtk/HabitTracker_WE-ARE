package com.example.habittracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.habittracker.R
import com.example.habittracker.model.AppDatabase
import com.example.habittracker.model.Habit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditHabitViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.buildDatabase(application)
    private val habitDao = db.habitDao()

    val habitLD = MutableLiveData<Habit>()
    val selectedIconIndex = MutableLiveData<Int>()
    val updateSuccess = MutableLiveData<Boolean>()

    private var habitId: Int = -1

    private val iconResources = listOf(
        R.drawable.muscle,
        R.drawable.book,
        R.drawable.food,
        R.drawable.wellness
    )

    fun loadHabit(id: Int) {
        habitId = id
        viewModelScope.launch(Dispatchers.IO) {
            val habit = habitDao.selectHabit(id)
            habitLD.postValue(habit)

            val idx = iconResources.indexOf(habit.icon)
            selectedIconIndex.postValue(if (idx >= 0) idx else 0)
        }
    }

    fun updateHabit(selectedIconPosition: Int) {
        val current = habitLD.value ?: return
        val icon = iconResources.getOrElse(selectedIconPosition) { current.icon }

        viewModelScope.launch(Dispatchers.IO) {
            habitDao.updateHabit(
                habitId,
                current.name,
                current.description,
                current.goal,
                current.unit,
                icon
            )
            updateSuccess.postValue(true)
        }
    }
}