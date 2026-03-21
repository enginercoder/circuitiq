package com.circuitiq.app.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
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

class HomeFragment : Fragment() {
    private var _b: FragmentHomeBinding? = null
    private val b get() = _b!!
    private val vm: HomeViewModel by viewModels()

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = FragmentHomeBinding.inflate(i,c,false); return b.root
    }

    override fun onViewCreated(v: View, s: Bundle?) {
        super.onViewCreated(v, s)
        val categories = CalculatorData.getCategories()

        val catAdapter = CategoryAdapter(categories) { cat ->
            val calcAdapter = CalculatorAdapter(cat.calculators) { calc -> openCalculator(calc) }
            b.rvCalculators.adapter = calcAdapter
            b.tvSectionTitle.text = cat.name
        }
        b.rvCategories.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        b.rvCategories.adapter = catAdapter

        val allCalcs = categories.flatMap { it.calculators }
        val calcAdapter = CalculatorAdapter(allCalcs) { calc -> openCalculator(calc) }
        b.rvCalculators.layoutManager = GridLayoutManager(requireContext(), 2)
        b.rvCalculators.adapter = calcAdapter
        b.tvSectionTitle.text = "All Calculators (${allCalcs.size})"

        vm.favorites.observe(viewLifecycleOwner) { favs ->
            b.tvFavCount.text = "${favs.size} Favourites"
        }
    }

    private fun openCalculator(calc: Calculator) {
        startActivity(Intent(requireContext(), CalculatorActivity::class.java).apply {
            putExtra("calc_id", calc.id)
            putExtra("calc_name", calc.name)
            putExtra("calc_category", calc.category)
        })
    }

    override fun onDestroyView() { super.onDestroyView(); _b=null }
}
