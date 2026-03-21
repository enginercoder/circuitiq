package com.circuitiq.app.ui.game

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.*
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.circuitiq.app.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private var _b: FragmentGameBinding? = null
    private val b get() = _b!!
    private var score = 0; private var lives = 3; private var questionIndex = 0
    private var timer: CountDownTimer? = null
    private val questions = ElectricityQuizData.getQuestions().shuffled()

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = FragmentGameBinding.inflate(i, c, false); return b.root
    }
    override fun onViewCreated(v: View, s: Bundle?) {
        super.onViewCreated(v, s); startGame(); b.btnRestart.setOnClickListener { startGame() }
    }
    private fun startGame() {
        score = 0; lives = 3; questionIndex = 0
        b.gameOverLayout.visibility = View.GONE; b.questionLayout.visibility = View.VISIBLE; showQuestion()
    }
    private fun showQuestion() {
        if (questionIndex >= questions.size) { showGameOver(); return }
        timer?.cancel()
        val q = questions[questionIndex]
        b.tvScore.text = "⚡ $score"; b.tvLives.text = "❤️".repeat(lives)
        b.tvQuestion.text = q.question; b.tvQNumber.text = "${questionIndex+1}/${questions.size}"
        b.progressTimer.max = 15; b.progressTimer.progress = 15
        val btns = listOf(b.btnA, b.btnB, b.btnC, b.btnD)
        btns.forEachIndexed { i, btn ->
            btn.text = q.options[i]; btn.setBackgroundColor(Color.parseColor("#2D1B4E"))
            btn.setTextColor(Color.WHITE); btn.isEnabled = true
            btn.setOnClickListener { checkAnswer(q.options[i], q.correct, btns) }
        }
        b.tvQuestion.startAnimation(AnimationUtils.loadAnimation(requireContext(), android.R.anim.slide_in_left))
        timer = object : CountDownTimer(15000, 1000) {
            override fun onTick(ms: Long) {
                val s = (ms/1000).toInt(); b.progressTimer.progress = s; b.tvTimer.text = "${s}s"
                b.tvTimer.setTextColor(if(s<=5) Color.RED else Color.parseColor("#FDCB6E"))
            }
            override fun onFinish() { timeUp(questions[questionIndex].correct, btns) }
        }.start()
    }
    private fun checkAnswer(sel: String, correct: String, btns: List<android.widget.Button>) {
        timer?.cancel(); btns.forEach { it.isEnabled = false }
        if (sel == correct) { score += 10; btns.first{it.text==sel}.setBackgroundColor(Color.parseColor("#06D6A0")); b.tvFeedback.text="✅ Correct! +10"; b.tvFeedback.setTextColor(Color.parseColor("#06D6A0")) }
        else { lives--; btns.first{it.text==sel}.setBackgroundColor(Color.parseColor("#EF476F")); btns.first{it.text==correct}.setBackgroundColor(Color.parseColor("#06D6A0")); b.tvFeedback.text="❌ Wrong! Answer: $correct"; b.tvFeedback.setTextColor(Color.parseColor("#EF476F")) }
        b.tvFeedback.visibility = View.VISIBLE
        if (lives<=0) b.root.postDelayed({showGameOver()},1500) else { questionIndex++; b.root.postDelayed({b.tvFeedback.visibility=View.GONE;showQuestion()},1500) }
    }
    private fun timeUp(correct: String, btns: List<android.widget.Button>) {
        btns.forEach{it.isEnabled=false}; btns.firstOrNull{it.text==correct}?.setBackgroundColor(Color.parseColor("#06D6A0"))
        b.tvFeedback.text="⏰ Time up!"; b.tvFeedback.setTextColor(Color.parseColor("#FDCB6E")); b.tvFeedback.visibility=View.VISIBLE
        lives--; if(lives<=0) b.root.postDelayed({showGameOver()},1500) else {questionIndex++;b.root.postDelayed({b.tvFeedback.visibility=View.GONE;showQuestion()},1500)}
    }
    private fun showGameOver() {
        timer?.cancel(); b.questionLayout.visibility=View.GONE; b.gameOverLayout.visibility=View.VISIBLE
        b.tvFinalScore.text="⚡ $score"
        b.tvGameOverMsg.text=when{score>=100->"Genius! 🏆";score>=60->"Great! 🎉";score>=30->"Good! 💪";else->"Keep trying! 📚"}
        b.tvFinalScore.startAnimation(AnimationUtils.loadAnimation(requireContext(), android.R.anim.fade_in))
    }
    override fun onDestroyView() { timer?.cancel(); super.onDestroyView(); _b=null }
}
