package com.example.quiz

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

sealed class MainState() {
    class DefaultState : MainState()
    class StartGameState : MainState()
    class SettingState : MainState()
    class ErrorState(var message: String) : MainState()

}

class MainViewModel : ViewModel() {
    var state = MutableLiveData<MainState>().apply { postValue(MainState.DefaultState()) }

    fun startQuiz() {
        state.postValue(MainState.StartGameState())
    }

    fun settings() {
        state.postValue(MainState.SettingState())
    }

}

