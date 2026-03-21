package com.circuitiq.app.adapter

import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.circuitiq.app.databinding.ItemFactBinding
import com.circuitiq.app.ui.didyouknow.Fact

class FactAdapter(private val items: List<Fact>) : RecyclerView.Adapter<FactAdapter.VH>() {
    inner class VH(private val b: ItemFactBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(f: Fact) {
            b.tvEmoji.text = f.emoji
            b.tvTitle.text = f.title
            b.tvFact.text = f.fact
            b.tvCategory.text = f.category
        }
    }
    override fun onCreateViewHolder(p: ViewGroup, t: Int) = VH(
        ItemFactBinding.inflate(LayoutInflater.from(p.context), p, false)
    )
    override fun onBindViewHolder(h: VH, pos: Int) = h.bind(items[pos])
    override fun getItemCount() = items.size
}
