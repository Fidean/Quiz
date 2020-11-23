package com.example.quiz

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quiz.RetrofitClient.api
import kotlinx.coroutines.*

sealed class MainState() {
    class DefaultState : MainState()
    class StartGameState : MainState()
    class SettingState : MainState()
    class ErrorState(var message: String) : MainState()

}

class MainViewModel : ViewModel() {
    var state = MutableLiveData<MainState>().apply { postValue(MainState.DefaultState()) }

    fun startQuiz() {
        CoroutineScope(Dispatchers.IO).launch {
            var getQuiz = api.getQuiz(10, "multiple")
            try {
                var quiz = getQuiz.await()
                state.postValue(MainState.StartGameState())
            } catch (error: Exception) {
                state.postValue(MainState.ErrorState("Network Error"))
            }
        }
    }
}

