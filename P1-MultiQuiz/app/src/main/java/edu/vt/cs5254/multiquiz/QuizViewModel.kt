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

    var showScore: Boolean = false

    val currentQuestionText: Int
        get() = questionBank[currentIndex].questionResId

    val currentAnswers: List<Answer>
        get() = questionBank[currentIndex].answerList


    fun submit() {
        moveToNext()
        if (currentIndex == 0){
            showScore = true
        }
    }

    fun selectAnswer(index: Int){
        val isCurrentlySelected = currentAnswers[index].isSelected

        currentAnswers.forEach {
            it.isSelected = false
        }

        if(!isCurrentlySelected){
            currentAnswers[index].isSelected = true
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

    fun resetAnswers() {
        questionBank.forEach{ answers ->
            answers.answerList.forEach{
                 it.isSelected = false
                 it.isEnabled = true
            }
        }
    }

    fun calculateScore(): Int {
        // If an answer is selected and correct, it's scored
        val correctSelections = questionBank.flatMap{
            it.answerList.filter{ answer ->
                answer.isSelected && answer.isCorrect
            }
        }

        // If the answer is disabled, a hint was used
        val hintsUsed = questionBank.flatMap {
            it.answerList.filter { answer ->
                !answer.isEnabled
            }
        }

        return correctSelections.size * 25 - hintsUsed.size * 7
    }

    private fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }



}