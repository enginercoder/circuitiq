package com.circuitiq.app.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.circuitiq.app.R

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        setContentView(R.layout.activity_splash)

        val ivIcon = findViewById<ImageView>(R.id.ivSplashIcon)
        val tvName = findViewById<TextView>(R.id.tvSplashName)
        val tvTagline = findViewById<TextView>(R.id.tvSplashTagline)
        val tvAuthor = findViewById<TextView>(R.id.tvSplashAuthor)

        // Icon: scale + fade in
        val iconAnim = AnimationSet(true).apply {
            addAnimation(ScaleAnimation(0.3f,1f,0.3f,1f,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f)
                .apply { duration=700; interpolator=OvershootInterpolator(2f) })
            addAnimation(AlphaAnimation(0f,1f).apply { duration=700 })
        }
        ivIcon.startAnimation(iconAnim)

        // App name: slide up + fade
        val nameAnim = AnimationSet(true).apply {
            addAnimation(TranslateAnimation(0f,0f,60f,0f).apply { duration=600; startOffset=400 })
            addAnimation(AlphaAnimation(0f,1f).apply { duration=600; startOffset=400 })
        }
        tvName.startAnimation(nameAnim)

        // Tagline
        val tagAnim = AlphaAnimation(0f,1f).apply { duration=600; startOffset=700 }
        tvTagline.startAnimation(tagAnim)

        // Author
        val authorAnim = AlphaAnimation(0f,1f).apply { duration=600; startOffset=900 }
        tvAuthor.startAnimation(authorAnim)

        // Navigate after 2.5s
        ivIcon.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }, 2500)
    }
}
