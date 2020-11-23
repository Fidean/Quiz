package com.example.quiz

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Quiz(
    var response_code: Int,
    var results: List<Question>
): Parcelable

@Parcelize
data class Question(
    var category: String,
    var difficulty: String,
    var question: String,
    var correct_answer: String,
    var incorrect_answers: List<String>
): Parcelable

