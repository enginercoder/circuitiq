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
import com.circuitiq.app.ui.MainActivity
import com.circuitiq.app.ui.calculator.CalculatorActivity
import com.circuitiq.app.util.CalculatorData
import java.util.Calendar

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
        updateGreeting()
        val categories = CalculatorData.getCategories()
        allCalcs = categories.flatMap { it.calculators }
        val catAdapter = CategoryAdapter(categories) { cat ->
            calcAdapter.updateList(cat.calculators)
            b.tvSectionTitle.text = cat.name
        }
        b.rvCategories.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        b.rvCategories.adapter = catAdapter
        calcAdapter = CalculatorAdapter(allCalcs) { calc -> openCalculator(calc) }
        b.rvCalculators.layoutManager = GridLayoutManager(requireContext(), 2)
        b.rvCalculators.adapter = calcAdapter
        b.rvCalculators.layoutAnimation = AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_anim)
        b.tvSectionTitle.text = "All Calculators (${allCalcs.size})"
        b.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val q = s.toString().trim().lowercase()
                if (q.isEmpty()) { calcAdapter.updateList(allCalcs); b.tvSectionTitle.text = "All Calculators (${allCalcs.size})" }
                else {
                    val f = allCalcs.filter { it.name.lowercase().contains(q) || it.category.lowercase().contains(q) || it.description.lowercase().contains(q) }
                    calcAdapter.updateList(f); b.tvSectionTitle.text = "${f.size} results"
                }
            }
            override fun beforeTextChanged(s: CharSequence?, st: Int, c: Int, a: Int) {}
            override fun onTextChanged(s: CharSequence?, st: Int, b: Int, a: Int) {}
        })
        b.btnDarkMode.setOnClickListener { MainActivity.toggleDarkMode(requireContext()); activity?.recreate() }
        b.btnShareApp.setOnClickListener {
            val text = "⚡ Check out CircuitIQ — Free electrical engineering calculator!\n48+ Calculators, 100% Offline\nhttps://play.google.com/store/apps/details?id=com.circuitiq.app\nBy Aditya Pal · EEE Engineer"
            startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND).apply { type="text/plain"; putExtra(Intent.EXTRA_TEXT,text) }, "Share CircuitIQ"))
        }
        b.btnRequestFeature.setOnClickListener {
            startActivity(Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:adityapaleee@gmail.com")).apply {
                putExtra(Intent.EXTRA_SUBJECT, "CircuitIQ Feature Request")
                putExtra(Intent.EXTRA_TEXT, "Hi Aditya,\n\nI would like to request:\n\n[Your feature here]")
            })
        }
        vm.favorites.observe(viewLifecycleOwner) { b.tvFavCount.text = "${it.size} ★" }
    }

    private fun updateGreeting() {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val greeting = when (hour) {
            in 5..11  -> "Good Morning!"
            in 12..16 -> "Good Afternoon!"
            in 17..20 -> "Good Evening!"
            else      -> "Good Night!"
        }
        val emoji = when (hour) {
            in 5..8   -> "🌅"
            in 9..11  -> "☀️"
            in 12..16 -> "🌤️"
            in 17..20 -> "🌆"
            in 21..23 -> "⭐"
            else      -> "🌙"
        }
        b.tvGreeting.text = greeting
        b.tvGreetingIcon.text = emoji
        b.tvSubtitle.text = "What will you calculate today?"
    }

    private fun openCalculator(calc: Calculator) {
        startActivity(Intent(requireContext(), CalculatorActivity::class.java).apply {
            putExtra("calc_id", calc.id); putExtra("calc_name", calc.name)
            putExtra("calc_category", calc.category); putExtra("calc_desc", calc.description)
        })
    }

    override fun onResume() { super.onResume(); updateGreeting() }
    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
