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
    private var score = 0; private var lives = 3; private var qi = 0
    private var timer: CountDownTimer? = null
    private val qs = ElectricityQuizData.getQuestions().shuffled()
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = FragmentGameBinding.inflate(i, c, false); return b.root
    }
    override fun onViewCreated(v: View, s: Bundle?) {
        super.onViewCreated(v, s); start()
        b.btnRestart.setOnClickListener { start() }
    }
    private fun start() {
        score = 0; lives = 3; qi = 0
        b.gameOverLayout.visibility = View.GONE
        b.questionLayout.visibility = View.VISIBLE
        show()
    }
    private fun show() {
        if (qi >= qs.size) { over(); return }
        timer?.cancel()
        val q = qs[qi]
        b.tvScore.text = "\u26a1 $score"
        b.tvLives.text = "\u2764\ufe0f".repeat(lives)
        b.tvQuestion.text = q.question
        b.tvQNumber.text = "${qi + 1}/${qs.size}"
        b.progressTimer.max = 15; b.progressTimer.progress = 15
        val btns = listOf(b.btnA, b.btnB, b.btnC, b.btnD)
        btns.forEachIndexed { idx, btn ->
            btn.text = q.options[idx]
            btn.setBackgroundColor(Color.parseColor("#2D1B4E"))
            btn.setTextColor(Color.WHITE)
            btn.isEnabled = true
            btn.setOnClickListener { check(q.options[idx], q.correct, btns) }
        }
        b.tvQuestion.startAnimation(AnimationUtils.loadAnimation(requireContext(), android.R.anim.slide_in_left))
        timer = object : CountDownTimer(15000, 1000) {
            override fun onTick(ms: Long) {
                val s = (ms / 1000).toInt()
                b.progressTimer.progress = s
                b.tvTimer.text = "${s}s"
                b.tvTimer.setTextColor(if (s <= 5) Color.RED else Color.parseColor("#FDCB6E"))
            }
            override fun onFinish() { timeup(qs[qi].correct, btns) }
        }.start()
    }
    private fun check(sel: String, correct: String, btns: List<android.widget.Button>) {
        timer?.cancel(); btns.forEach { it.isEnabled = false }
        if (sel == correct) {
            score += 10
            btns.first { it.text == sel }.setBackgroundColor(Color.parseColor("#06D6A0"))
            b.tvFeedback.text = "\u2705 Correct! +10"
            b.tvFeedback.setTextColor(Color.parseColor("#06D6A0"))
        } else {
            lives--
            btns.first { it.text == sel }.setBackgroundColor(Color.parseColor("#EF476F"))
            btns.first { it.text == correct }.setBackgroundColor(Color.parseColor("#06D6A0"))
            b.tvFeedback.text = "\u274c Wrong! Answer: $correct"
            b.tvFeedback.setTextColor(Color.parseColor("#EF476F"))
        }
        b.tvFeedback.visibility = View.VISIBLE
        if (lives <= 0) b.root.postDelayed({ over() }, 1500)
        else { qi++; b.root.postDelayed({ b.tvFeedback.visibility = View.GONE; show() }, 1500) }
    }
    private fun timeup(correct: String, btns: List<android.widget.Button>) {
        btns.forEach { it.isEnabled = false }
        btns.firstOrNull { it.text == correct }?.setBackgroundColor(Color.parseColor("#06D6A0"))
        b.tvFeedback.text = "\u23f0 Time up!"
        b.tvFeedback.setTextColor(Color.parseColor("#FDCB6E"))
        b.tvFeedback.visibility = View.VISIBLE
        lives--
        if (lives <= 0) b.root.postDelayed({ over() }, 1500)
        else { qi++; b.root.postDelayed({ b.tvFeedback.visibility = View.GONE; show() }, 1500) }
    }
    private fun over() {
        timer?.cancel()
        b.questionLayout.visibility = View.GONE
        b.gameOverLayout.visibility = View.VISIBLE
        b.tvFinalScore.text = "\u26a1 $score"
        b.tvGameOverMsg.text = when {
            score >= 100 -> "Genius! \U0001f3c6"
            score >= 60  -> "Great! \U0001f389"
            score >= 30  -> "Good! \U0001f4aa"
            else         -> "Keep trying! \U0001f4da"
        }
        b.tvFinalScore.startAnimation(AnimationUtils.loadAnimation(requireContext(), android.R.anim.fade_in))
    }
    override fun onDestroyView() { timer?.cancel(); super.onDestroyView(); _b = null }
}
