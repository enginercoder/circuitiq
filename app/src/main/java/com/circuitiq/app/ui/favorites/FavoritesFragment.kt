package com.circuitiq.app.ui.favorites

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.circuitiq.app.adapter.FavoriteAdapter
import com.circuitiq.app.databinding.FragmentFavoritesBinding
import com.circuitiq.app.ui.calculator.CalculatorActivity

class FavoritesFragment : Fragment() {
    private var _b: FragmentFavoritesBinding? = null
    private val b get() = _b!!
    private val vm: FavoritesViewModel by viewModels()

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = FragmentFavoritesBinding.inflate(i,c,false); return b.root
    }

    override fun onViewCreated(v: View, s: Bundle?) {
        super.onViewCreated(v, s)
        val adapter = FavoriteAdapter(
            onClick = { fav -> startActivity(Intent(requireContext(), CalculatorActivity::class.java).apply { putExtra("calc_id",fav.calculatorId); putExtra("calc_name",fav.calculatorName); putExtra("calc_category",fav.category) }) },
            onRemove = { fav -> vm.remove(fav.calculatorId) }
        )
        b.rv.layoutManager = LinearLayoutManager(requireContext())
        b.rv.adapter = adapter
        vm.favorites.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            b.tvEmpty.visibility = if(it.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _b=null }
}
