package com.circuitiq.app.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.circuitiq.app.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {
    private var _b: FragmentAboutBinding? = null
    private val b get() = _b!!

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = FragmentAboutBinding.inflate(i,c,false); return b.root
    }

    override fun onViewCreated(v: View, s: Bundle?) {
        super.onViewCreated(v, s)
        b.btnLinkedIn.setOnClickListener { openUrl("https://www.linkedin.com/in/aditya-pal-435130303") }
        b.btnInstagram.setOnClickListener { openUrl("https://www.instagram.com/adixoeee") }
        b.btnEmail.setOnClickListener { startActivity(Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:adityapaleee@gmail.com"))) }
    }

    private fun openUrl(url: String) { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url))) }
    override fun onDestroyView() { super.onDestroyView(); _b=null }
}
