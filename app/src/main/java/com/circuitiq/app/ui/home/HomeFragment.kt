package com.circuitiq.app.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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

        // Greeting
        b.tvGreeting.text = getGreeting()
        b.tvSubtitle.text = "What will you calculate today?"

        val categories = CalculatorData.getCategories()
        allCalcs = categories.flatMap { it.calculators }

        // Category chips
        val catAdapter = CategoryAdapter(categories) { cat ->
            val filtered = if (cat.id == "all") allCalcs else cat.calculators
            calcAdapter.submitList(filtered)
            b.tvSectionTitle.text = if (cat.id == "all") "All Calculators (${allCalcs.size})" else cat.name
        }
        b.rvCategories.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        b.rvCategories.adapter = catAdapter

        // Calculators grid
        calcAdapter = CalculatorAdapter(allCalcs) { calc -> openCalculator(calc) }
        b.rvCalculators.layoutManager = GridLayoutManager(requireContext(), 2)
        b.rvCalculators.adapter = calcAdapter
        b.tvSectionTitle.text = "All Calculators (${allCalcs.size})"

        // Search
        b.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val q = s.toString().trim().lowercase()
                if (q.isEmpty()) {
                    calcAdapter.submitList(allCalcs)
                    b.tvSectionTitle.text = "All Calculators (${allCalcs.size})"
                } else {
                    val filtered = allCalcs.filter {
                        it.name.lowercase().contains(q) || it.category.lowercase().contains(q) || it.description.lowercase().contains(q)
                    }
                    calcAdapter.submitList(filtered)
                    b.tvSectionTitle.text = "${filtered.size} results for \"$q\""
                }
            }
            override fun beforeTextChanged(s: CharSequence?, st: Int, c: Int, a: Int) {}
            override fun onTextChanged(s: CharSequence?, st: Int, b: Int, a: Int) {}
        })

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

    override fun onResume() { super.onResume(); vm.refresh(); b.tvGreeting.text = getGreeting() }
    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
