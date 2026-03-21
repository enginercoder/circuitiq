package com.circuitiq.app.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.circuitiq.app.R

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        setContentView(R.layout.activity_splash)

        val glowRing = findViewById<View>(R.id.glowRing)
        val ivIcon = findViewById<View>(R.id.ivSplashIcon)
        val tvName = findViewById<TextView>(R.id.tvSplashName)
        val tvTagline = findViewById<TextView>(R.id.tvSplashTagline)
        val badgeRow = findViewById<LinearLayout>(R.id.badgeRow)
        val tvAuthor = findViewById<TextView>(R.id.tvSplashAuthor)
        val dotLoader = findViewById<LinearLayout>(R.id.dotLoader)
        val dot1 = findViewById<View>(R.id.dot1)
        val dot2 = findViewById<View>(R.id.dot2)
        val dot3 = findViewById<View>(R.id.dot3)

        // Glow ring fades in
        glowRing.animate().alpha(1f).setDuration(600).setStartDelay(100).start()

        // Icon bounces in
        ivIcon.scaleX = 0.3f; ivIcon.scaleY = 0.3f
        ivIcon.animate().alpha(1f).scaleX(1f).scaleY(1f)
            .setDuration(700).setStartDelay(200)
            .setInterpolator(OvershootInterpolator(1.8f)).start()

        // App name slides up
        tvName.translationY = 60f
        tvName.animate().alpha(1f).translationY(0f)
            .setDuration(600).setStartDelay(600)
            .setInterpolator(DecelerateInterpolator()).start()

        // Tagline fades
        tvTagline.animate().alpha(1f).setDuration(500).setStartDelay(850).start()

        // Badges
        badgeRow.animate().alpha(1f).setDuration(500).setStartDelay(1050).start()

        // Author
        tvAuthor.animate().alpha(1f).setDuration(500).setStartDelay(1200).start()

        // Dot loader appears then bounces
        dotLoader.animate().alpha(1f).setDuration(400).setStartDelay(1300).withEndAction {
            bounceDot(dot1, 0)
            bounceDot(dot2, 180)
            bounceDot(dot3, 360)
        }.start()

        // Navigate after 3.2s
        ivIcon.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            overridePendingTransition(R.anim.fade_in, android.R.anim.fade_out)
        }, 3200)
    }

    private fun bounceDot(dot: View, delay: Long) {
        dot.postDelayed({
            dot.animate().translationY(-18f).setDuration(280)
                .withEndAction {
                    dot.animate().translationY(0f).setDuration(280).withEndAction {
                        bounceDot(dot, 0)
                    }.start()
                }.start()
        }, delay)
    }
}
