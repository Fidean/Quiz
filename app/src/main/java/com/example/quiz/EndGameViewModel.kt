package com.example.quiz

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

sealed class EndGameState() {
    class DefaultState : EndGameState()
    class ErrorState(var message: String) : EndGameState()
}

class EndGameViewModel : ViewModel() {
    var state = MutableLiveData<EndGameState>().apply { postValue(EndGameState.DefaultState()) }
    var correctAnswers: Int = 0
    fun setCorrectAnswer(value: Int?) {
        if (value != null) {
            Log.d("EndGameLog", value.toString())
            correctAnswers = value
        } else {
            state.postValue(EndGameState.ErrorState("Get Bundle Error"))
        }
    }
}