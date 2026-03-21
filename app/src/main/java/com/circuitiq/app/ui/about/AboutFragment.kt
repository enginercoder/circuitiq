package com.circuitiq.app.ui.about

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.circuitiq.app.R
import com.circuitiq.app.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {
    private var _b: FragmentAboutBinding? = null
    private val b get() = _b!!
    private var isBulbOn = false

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = FragmentAboutBinding.inflate(i, c, false); return b.root
    }

    override fun onViewCreated(v: View, s: Bundle?) {
        super.onViewCreated(v, s)

        b.tvBulb.setOnClickListener {
            // Tap bounce animation
            b.tvBulb.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.bulb_tap))
            isBulbOn = !isBulbOn
            if (isBulbOn) turnOn() else turnOff()
        }

        b.btnLinkedIn.setOnClickListener { openUrl("https://www.linkedin.com/in/aditya-pal-435130303") }
        b.btnInstagram.setOnClickListener { openUrl("https://www.instagram.com/adixoeee") }
        b.btnEmail.setOnClickListener {
            startActivity(Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:adityapaleee@gmail.com")))
        }
    }

    private fun turnOn() {
        // Switch to glowing bulb emoji
        b.tvBulb.text = "💡"
        b.tvBulbHint.text = "✨ Tap to turn off"

        // Show glow rings
        b.glowRing1.visibility = View.VISIBLE
        b.glowRing2.visibility = View.VISIBLE

        // Animate glow rings
        b.glowRing1.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.bulb_glow_pulse))
        b.glowRing2.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.bulb_glow_pulse2))

        // Yellow tint on bulb text
        b.tvBulb.setTextColor(Color.parseColor("#FFD700"))
    }

    private fun turnOff() {
        // Switch to dark bulb
        b.tvBulb.text = "🌑"
        b.tvBulbHint.text = "👆 Tap to turn on!"

        // Stop and hide glow rings
        b.glowRing1.clearAnimation()
        b.glowRing2.clearAnimation()
        b.glowRing1.visibility = View.INVISIBLE
        b.glowRing2.visibility = View.INVISIBLE

        // Reset tint
        b.tvBulb.setTextColor(Color.WHITE)
    }

    private fun openUrl(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun onDestroyView() {
        b.glowRing1.clearAnimation()
        b.glowRing2.clearAnimation()
        super.onDestroyView()
        _b = null
    }
}
