package com.example.habittracker.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.habittracker.R
import com.example.habittracker.model.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditHabitViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.buildDatabase(application)
    private val habitDao = db.habitDao()

    val name = ObservableField("")
    val description = ObservableField("")
    val goal = ObservableField("")
    val unit = ObservableField("")

    val selectedIconIndex = MutableLiveData<Int>()
    val updateSuccess = MutableLiveData<Boolean>()

    private var habitId: Int = -1
    private var currentIcon: Int = 0

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

            name.set(habit.name)
            description.set(habit.description)
            goal.set(habit.goal.toString())
            unit.set(habit.unit)
            currentIcon = habit.icon

            val idx = iconResources.indexOf(habit.icon)
            selectedIconIndex.postValue(if (idx >= 0) idx else 0)
        }
    }

    fun updateHabit(selectedIconPosition: Int) {
        val goalInt = goal.get()?.toIntOrNull() ?: 0
        val icon = iconResources.getOrElse(selectedIconPosition) { currentIcon }

        viewModelScope.launch(Dispatchers.IO) {
            habitDao.updateHabit(
                habitId,
                name.get().orEmpty(),
                description.get().orEmpty(),
                goalInt,
                unit.get().orEmpty(),
                icon
            )
            updateSuccess.postValue(true)
        }
    }
}