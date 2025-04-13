package edu.vt.cs5254.multiquiz

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import edu.vt.cs5254.multiquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Name: Johann Ruiz
    // Username: johannruiz176

    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()

    private val scoreLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Handle the result
        if (result.data?.getBooleanExtra(RESET, false) == true) {
            quizViewModel.resetAnswers()
        }
        updateView()
    }

    private lateinit var buttons: List<Button>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buttons =  listOf(
            binding.answer0Button,
            binding.answer1Button,
            binding.answer2Button,
            binding.answer3Button,
        )

        binding.answer0Button.setOnClickListener {
            quizViewModel.selectAnswer(0)
            updateView()
        }

        binding.answer1Button.setOnClickListener {
            quizViewModel.selectAnswer(1)
            updateView()
        }

        binding.answer2Button.setOnClickListener {
            quizViewModel.selectAnswer(2)
            updateView()
        }

        binding.answer3Button.setOnClickListener {
            quizViewModel.selectAnswer(3)
            updateView()
        }

        binding.hintButton.setOnClickListener {
            quizViewModel.hint()
            updateView()
        }

        binding.submitButton.setOnClickListener {
            quizViewModel.submit()
            if (quizViewModel.showScore) {
                val intent = ScoreActivity.newIntent(this@MainActivity, quizViewModel.calculateScore())
                scoreLauncher.launch(intent)
                quizViewModel.showScore = false
            }
            else{
                updateView()
            }
        }

        updateView()

    }

    private fun updateView(){
        quizViewModel.currentAnswers.let { answers ->
            answers.zip(buttons).forEach { (answer, button) ->
                button.isEnabled = answer.isEnabled
                button.isSelected = answer.isSelected
                button.updateColor()
                button.setText(answer.textResId)
            }
            binding.submitButton.isEnabled = answers.any{ it.isSelected }
            binding.hintButton.isEnabled = answers.filter{ it.isEnabled }.size != 1

        }
        binding.questionTextView.setText(quizViewModel.currentQuestionText)
    }


}