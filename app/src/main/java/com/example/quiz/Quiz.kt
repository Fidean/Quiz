package com.example.quiz

data class Question(
    var category: String? = null,
    var difficulty: Difficulty? = null ,
    var question: String? = null,
    var correct_answer: String? = null,
    var incorrect_answers: List<String>
)

enum class Difficulty(difficulty: String) {
    EASY("easy"),
    MEDIUM("medium"),
    HARD("hard")
}
