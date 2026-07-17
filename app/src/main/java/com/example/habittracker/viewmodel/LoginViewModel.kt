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
import com.example.habittracker.util.SessionManager

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

            if (user != null) {
                SessionManager(getApplication()).saveSession(user.id)
            }

            loginResultLD.postValue(user)
        }
    }

    override fun onCleared() {
        super.onCleared()

        job.cancel()
    }
}