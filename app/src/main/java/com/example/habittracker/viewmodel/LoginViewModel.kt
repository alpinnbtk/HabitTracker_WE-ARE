package com.example.habittracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

import com.example.habittracker.model.User
import com.example.habittracker.model.AppDatabase

class LoginViewModel(application: Application)
    : AndroidViewModel(application), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    val loginResultLD = MutableLiveData<User?>()

    fun login(username: String, password: String) {
        launch {
            val db = AppDatabase.buildDatabase(getApplication())
            val user = db.userDao().login(username, password)
            loginResultLD.postValue(user)
        }
    }

    override fun onCleared() {
        super.onCleared()

        job.cancel()
    }
}