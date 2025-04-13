package edu.vt.cs5254.multiquiz

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModel


class ScoreViewModel : ViewModel() {

    var quizScore: String? = null
    var resetButtonIsEnabled: Boolean = true

    fun setReset() {
        resetButtonIsEnabled = false
        quizScore = "?"
    }

}