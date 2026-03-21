package com.circuitiq.app.ui.game

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.*
import android.view.animation.*
import androidx.fragment.app.Fragment
import com.circuitiq.app.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private var _b: FragmentGameBinding? = null
    private val b get() = _b!!

    private var score = 0
    private var lives = 3
    private var questionIndex = 0
    private var timer: CountDownTimer? = null
    private val questions = ElectricityQuizData.getQuestions().shuffled()

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = FragmentGameBinding.inflate(i, c, false); return b.root
    }

    override fun onViewCreated(v: View, s: Bundle?) {
        super.onViewCreated(v, s)
        startGame()
        b.btnRestart.setOnClickListener { restartGame() }
    }

    private fun startGame() {
        score = 0; lives = 3; questionIndex = 0
        b.gameOverLayout.visibility = View.GONE
        b.questionLayout.visibility = View.VISIBLE
        showQuestion()
    }

    private fun showQuestion() {
        if (questionIndex >= questions.size) { showGameOver(true); return }
        timer?.cancel()
        val q = questions[questionIndex]
        b.tvScore.text = "⚡ $score"
        b.tvLives.text = "❤️".repeat(lives)
        b.tvQuestion.text = q.question
        b.tvQNumber.text = "${questionIndex + 1}/${questions.size}"
        b.progressTimer.max = 15
        b.progressTimer.progress = 15

        val buttons = listOf(b.btnA, b.btnB, b.btnC, b.btnD)
        buttons.forEachIndexed { i, btn ->
            btn.text = q.options[i]
            btn.setBackgroundColor(Color.parseColor("#2D1B4E"))
            btn.setTextColor(Color.WHITE)
            btn.isEnabled = true
            btn.setOnClickListener { checkAnswer(q.options[i], q.correct, buttons) }
        }

        // Slide in animation
        val anim = AnimationUtils.loadAnimation(requireContext(), android.R.anim.slide_in_left)
        b.tvQuestion.startAnimation(anim)

        // Countdown timer
        timer = object : CountDownTimer(15000, 1000) {
            override fun onTick(ms: Long) {
                val secs = (ms / 1000).toInt()
                b.progressTimer.progress = secs
                b.tvTimer.text = "${secs}s"
                if (secs <= 5) b.tvTimer.setTextColor(Color.RED)
                else b.tvTimer.setTextColor(Color.parseColor("#FFD700"))
            }
            override fun onFinish() {
                timeUp(questions[questionIndex].correct, buttons)
            }
        }.start()
    }

    private fun checkAnswer(selected: String, correct: String, buttons: List<android.widget.Button>) {
        timer?.cancel()
        buttons.forEach { it.isEnabled = false }
        if (selected == correct) {
            score += 10
            buttons.first { it.text == selected }.setBackgroundColor(Color.parseColor("#06D6A0"))
            b.tvFeedback.text = "✅ Correct! +10"
            b.tvFeedback.setTextColor(Color.parseColor("#06D6A0"))
        } else {
            lives--
            buttons.first { it.text == selected }.setBackgroundColor(Color.parseColor("#EF476F"))
            buttons.first { it.text == correct }.setBackgroundColor(Color.parseColor("#06D6A0"))
            b.tvFeedback.text = "❌ Wrong! Answer: $correct"
            b.tvFeedback.setTextColor(Color.parseColor("#EF476F"))
        }
        b.tvFeedback.visibility = View.VISIBLE
        if (lives <= 0) {
            b.root.postDelayed({ showGameOver(false) }, 1500)
        } else {
            questionIndex++
            b.root.postDelayed({ b.tvFeedback.visibility = View.GONE; showQuestion() }, 1500)
        }
    }

    private fun timeUp(correct: String, buttons: List<android.widget.Button>) {
        buttons.forEach { it.isEnabled = false }
        buttons.firstOrNull { it.text == correct }?.setBackgroundColor(Color.parseColor("#06D6A0"))
        b.tvFeedback.text = "⏰ Time up! Answer: $correct"
        b.tvFeedback.setTextColor(Color.parseColor("#FFD700"))
        b.tvFeedback.visibility = View.VISIBLE
        lives--
        if (lives <= 0) b.root.postDelayed({ showGameOver(false) }, 1500)
        else { questionIndex++; b.root.postDelayed({ b.tvFeedback.visibility = View.GONE; showQuestion() }, 1500) }
    }

    private fun showGameOver(won: Boolean) {
        timer?.cancel()
        b.questionLayout.visibility = View.GONE
        b.gameOverLayout.visibility = View.VISIBLE
        b.tvFinalScore.text = "⚡ $score"
        b.tvGameOverMsg.text = when {
            score >= 100 -> "Genius! 🏆 You know your circuits!"
            score >= 60  -> "Great work! 🎉 Keep learning!"
            score >= 30  -> "Good effort! 💪 Practice more!"
            else         -> "Keep trying! 📚 Study the facts!"
        }
        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.scale_up)
        b.tvFinalScore.startAnimation(anim)
    }

    private fun restartGame() { startGame() }

    override fun onDestroyView() { timer?.cancel(); super.onDestroyView(); _b = null }
}
