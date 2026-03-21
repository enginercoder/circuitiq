package com.circuitiq.app.ui.didyouknow

import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.circuitiq.app.R
import com.circuitiq.app.adapter.FactAdapter
import com.circuitiq.app.databinding.FragmentDidYouKnowBinding

class DidYouKnowFragment : Fragment() {
    private var _b: FragmentDidYouKnowBinding? = null
    private val b get() = _b!!

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = FragmentDidYouKnowBinding.inflate(i, c, false); return b.root
    }

    override fun onViewCreated(v: View, s: Bundle?) {
        super.onViewCreated(v, s)
        val facts = DidYouKnowData.getFacts()
        val adapter = FactAdapter(facts)
        b.rvFacts.layoutManager = LinearLayoutManager(requireContext())
        b.rvFacts.adapter = adapter
        b.rvFacts.layoutAnimation = AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_anim)
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
