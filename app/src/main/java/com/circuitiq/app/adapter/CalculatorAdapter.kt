package com.circuitiq.app.adapter

import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.circuitiq.app.data.model.Calculator
import com.circuitiq.app.databinding.ItemCalculatorBinding

class CalculatorAdapter(
    private var items: List<Calculator>,
    private val onClick: (Calculator) -> Unit
) : RecyclerView.Adapter<CalculatorAdapter.VH>() {

    fun updateList(newItems: List<Calculator>) {
        items = newItems
        notifyDataSetChanged()
    }

    inner class VH(private val b: ItemCalculatorBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(calc: Calculator) {
            b.tvIcon.text = calc.icon
            b.tvName.text = calc.name
            b.tvCategory.text = calc.category
            b.root.setOnClickListener { onClick(calc) }
        }
    }

    override fun onCreateViewHolder(p: ViewGroup, t: Int) = VH(
        ItemCalculatorBinding.inflate(LayoutInflater.from(p.context), p, false)
    )
    override fun onBindViewHolder(h: VH, pos: Int) = h.bind(items[pos])
    override fun getItemCount() = items.size
}
