package edu.vt.cs5254.multiquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import edu.vt.cs5254.multiquiz.databinding.ActivityScoreBinding

const val RESET = "edu.vt.cs5254.multiquiz.reset"

private const val QUIZ_SCORE =
    "edu.vt.cs5254.multiquiz.score"

class ScoreActivity : AppCompatActivity() {

    // Name: Johann Ruiz
    // Username: johannruiz176

    private lateinit var binding: ActivityScoreBinding

    private val scoreViewModel: ScoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (scoreViewModel.quizScore == null) {
            scoreViewModel.quizScore = intent.getIntExtra(QUIZ_SCORE, 0).toString()
        }

        binding.resetButton.setOnClickListener {
            scoreViewModel.setReset()
            updateView()
        }

        updateView()

    }

    private fun updateView(){
        binding.scoreText.text = scoreViewModel.quizScore
        binding.resetButton.isEnabled = scoreViewModel.resetButtonIsEnabled
        val data = Intent().apply {
            putExtra(RESET, !scoreViewModel.resetButtonIsEnabled)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        fun newIntent(packageContext: Context, quizScore: Int): Intent {
            return Intent(packageContext, ScoreActivity::class.java).apply {
                putExtra(QUIZ_SCORE, quizScore)
            }
        }
    }
}