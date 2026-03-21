package com.circuitiq.app.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.circuitiq.app.R
import com.circuitiq.app.adapter.CategoryAdapter
import com.circuitiq.app.adapter.CalculatorAdapter
import com.circuitiq.app.data.model.Calculator
import com.circuitiq.app.databinding.FragmentHomeBinding
import com.circuitiq.app.ui.calculator.CalculatorActivity
import com.circuitiq.app.util.CalculatorData
import com.circuitiq.app.util.getGreeting

class HomeFragment : Fragment() {
    private var _b: FragmentHomeBinding? = null
    private val b get() = _b!!
    private val vm: HomeViewModel by viewModels()
    private lateinit var calcAdapter: CalculatorAdapter
    private var allCalcs = listOf<Calculator>()

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = FragmentHomeBinding.inflate(i, c, false); return b.root
    }

    override fun onViewCreated(v: View, s: Bundle?) {
        super.onViewCreated(v, s)

        b.tvGreeting.text = getGreeting()
        
        b.tvSubtitle.text = "What will you calculate today?"

        val categories = CalculatorData.getCategories()
        allCalcs = categories.flatMap { it.calculators }

        val catAdapter = CategoryAdapter(categories) { cat ->
            calcAdapter.updateList(cat.calculators)
            b.tvSectionTitle.text = cat.name
        }
        b.rvCategories.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        b.rvCategories.adapter = catAdapter

        calcAdapter = CalculatorAdapter(allCalcs) { calc -> openCalculator(calc) }
        b.rvCalculators.layoutManager = GridLayoutManager(requireContext(), 2)
        b.rvCalculators.adapter = calcAdapter
        b.rvCalculators.layoutAnimation = AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_anim)
        b.tvSectionTitle.text = "All Calculators (${allCalcs.size})"

        b.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val q = s.toString().trim().lowercase()
                if (q.isEmpty()) {
                    calcAdapter.updateList(allCalcs)
                    b.tvSectionTitle.text = "All Calculators (${allCalcs.size})"
                } else {
                    val filtered = allCalcs.filter {
                        it.name.lowercase().contains(q) ||
                        it.category.lowercase().contains(q) ||
                        it.description.lowercase().contains(q)
                    }
                    calcAdapter.updateList(filtered)
                    b.tvSectionTitle.text = "${filtered.size} results for \"$q\""
                }
            }
            override fun beforeTextChanged(s: CharSequence?, st: Int, c: Int, a: Int) {}
            override fun onTextChanged(s: CharSequence?, st: Int, b: Int, a: Int) {}
        })

        // Share App
        b.btnShareApp.setOnClickListener {
            val text = "⚡ Check out CircuitIQ — The best FREE electrical engineering calculator app!\n\n" +
                "✅ 48+ Calculators\n📴 Works Offline\n🆓 100% Free\n\n" +
                "Download: https://play.google.com/store/apps/details?id=com.circuitiq.app\n\n" +
                "Built by Aditya Pal · EEE Engineer"
            startActivity(Intent.createChooser(
                Intent(Intent.ACTION_SEND).apply { type = "text/plain"; putExtra(Intent.EXTRA_TEXT, text) },
                "Share CircuitIQ"
            ))
        }

        // Request Feature
        b.btnRequestFeature.setOnClickListener {
            val subject = "CircuitIQ - Feature Request"
            val body = "Hi Aditya,\n\nI would like to request a new feature/calculator:\n\n[Describe your feature here]\n\nApp Version: 1.0.0\nDevice: Android"
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:adityapaleee@gmail.com")).apply {
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, body)
            }
            startActivity(Intent.createChooser(intent, "Send Feature Request"))
        }

        vm.favorites.observe(viewLifecycleOwner) { favs ->
            b.tvFavCount.text = "${favs.size} ★"
        }
    }

    private fun openCalculator(calc: Calculator) {
        startActivity(Intent(requireContext(), CalculatorActivity::class.java).apply {
            putExtra("calc_id", calc.id)
            putExtra("calc_name", calc.name)
            putExtra("calc_category", calc.category)
            putExtra("calc_desc", calc.description)
        })
    }

    override fun onResume() {
        super.onResume()
        b.tvGreeting.text = getGreeting()
        
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
