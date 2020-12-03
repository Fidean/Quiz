package com.example.quiz

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

sealed class EndGameState {
    object DefaultState : EndGameState()
    object RestartState : EndGameState()
    class ErrorState(var message: String) : EndGameState()
}

class EndGameViewModel : ViewModel() {
    var state = MutableLiveData<EndGameState>().apply { postValue(EndGameState.DefaultState) }
    private var correctAnswers: Int = 0
    fun restart() {
        state.postValue(EndGameState.RestartState)
    }

    fun setCorrectAnswer(value: Int?) {
        if (value != null) {
            Log.d("EndGameLog", value.toString())
            correctAnswers = value
        } else {
            state.postValue(EndGameState.ErrorState("Get Bundle Error"))
        }
    }

    fun getCorrectAnswer(): String {
        return correctAnswers.toString()
    }
}