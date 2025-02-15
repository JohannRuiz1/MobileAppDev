package edu.vt.cs5254.multiquiz

import androidx.lifecycle.ViewModel


class QuizViewModel : ViewModel() {

    private val questionBank =  listOf(
        Question(R.string.question_germany, listOf(
            Answer(R.string.answer_germany_1, true),
            Answer(R.string.answer_germany_2, false),
            Answer(R.string.answer_germany_3, false),
            Answer(R.string.answer_germany_4, false)
        )),
        Question(R.string.question_iphone, listOf(
            Answer(R.string.answer_iphone_1, false),
            Answer(R.string.answer_iphone_2, true),
            Answer(R.string.answer_iphone_3, false),
            Answer(R.string.answer_iphone_4, false)
        )),
        Question(R.string.question_secrets_management, listOf(
            Answer(R.string.answer_sm_1, false),
            Answer(R.string.answer_sm_2, false),
            Answer(R.string.answer_sm_3, true),
            Answer(R.string.answer_sm_4, false)
        )),
        Question(R.string.question_longest_lifespan, listOf(
            Answer(R.string.answer_ll_1, false),
            Answer(R.string.answer_ll_2, false),
            Answer(R.string.answer_ll_3, false),
            Answer(R.string.answer_ll_4, true)
        ))
    )

    private var currentIndex: Int = 0

    val currentQuestionText: Int
        get() = questionBank[currentIndex].questionResId

    val currentAnswers: List<Answer>
        get() = questionBank[currentIndex].answerList

    fun submit() {
        resetAnswers()
        moveToNext()
    }

    fun selectAnswer(index: Int){
        currentAnswers.forEach {
            it.isSelected = false
        }

        currentAnswers[index].apply {
            isSelected = true
        }
    }

    fun hint() {
        currentAnswers.filter {
            !it.isCorrect && it.isEnabled
        }.shuffled().first().apply {
            isEnabled = false
            isSelected = false
        }
    }

    private fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    private fun resetAnswers() {
        currentAnswers.forEach {
            it.isSelected = false
            it.isEnabled = true
        }
    }

}