package com.example.quiz

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

sealed class QuizState() {
    class LoddingState : QuizState()
    class StartQuizState : QuizState()
    class ButtonClickState : QuizState()
    class EndQuizState : QuizState()
    class ErrorState(var message: String) : QuizState()
}

class QuizViewModel : ViewModel() {
    var state = MutableLiveData<QuizState>().apply { postValue(QuizState.LoddingState()) }
    var questionNumber = MutableLiveData<Int>().apply { postValue(0) }
    var quiz = MutableLiveData<Quiz>()
    var correctAnswers = MutableLiveData<Int>().apply { postValue(0) }
    private var difficulty = "medium"


    fun loadQuiz() {
        CoroutineScope(Dispatchers.IO).launch {
            var getQuiz = RetrofitClient.api.getQuiz(10,"multiple",difficulty)
            try {
                quiz.postValue(getQuiz.await())
                state.postValue(QuizState.StartQuizState())
            } catch (error: Exception) {
                state.postValue(QuizState.ErrorState("Network Error"))
            }
        }
    }


    fun checkQuestion() {
        if (questionNumber.value!! < 9) {
            questionNumber.postValue(questionNumber.value!!.plus(1))
            state.postValue(QuizState.ButtonClickState())
        } else {
            state.postValue(QuizState.EndQuizState())
        }
    }

    fun correctAnswer() {
        correctAnswers.postValue(correctAnswers.value!!.plus(1))
    }

    fun setDifficulty(value:String){
        Log.d("QuizVMLog", value)
        difficulty = value
    }

}