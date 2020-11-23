package com.example.quiz

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

sealed class QuizState() {
    class LoddingState : QuizState()
    class StartQuizState : QuizState()
    class ButtonClickState : QuizState()
    class ErrorState(var message: String) : QuizState()
}

class QuizViewModel : ViewModel() {
    var state = MutableLiveData<QuizState>().apply { postValue(QuizState.LoddingState()) }
    var questionNumber = MutableLiveData<Int>().apply { postValue(0) }
    var quiz = MutableLiveData<Quiz>()




    fun loadQuiz() {
        CoroutineScope(Dispatchers.IO).launch {
            var getQuiz = RetrofitClient.api.getQuiz(10, "multiple")
            try {
                quiz.postValue(getQuiz.await())
                state.postValue(QuizState.StartQuizState())
            } catch (error: Exception) {
                state.postValue(QuizState.ErrorState("Network Error"))
            }
        }
    }


    fun nextQuestion() {
        questionNumber.postValue(questionNumber.value!!.plus(1))
        state.postValue(QuizState.ButtonClickState())
    }
}