package com.circuitiq.app.adapter

import android.view.*
import androidx.recyclerview.widget.*
import com.circuitiq.app.data.model.Favorite
import com.circuitiq.app.databinding.ItemFavoriteBinding

class FavoriteAdapter(
    private val onClick: (Favorite) -> Unit,
    private val onRemove: (Favorite) -> Unit
) : ListAdapter<Favorite, FavoriteAdapter.VH>(DIFF) {
    inner class VH(private val b: ItemFavoriteBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(f: Favorite) {
            b.tvIcon.text = f.icon
            b.tvName.text = f.calculatorName
            b.tvCategory.text = f.category
            b.root.setOnClickListener { onClick(f) }
            b.btnRemove.setOnClickListener { onRemove(f) }
        }
    }
    override fun onCreateViewHolder(p: ViewGroup, t: Int) = VH(ItemFavoriteBinding.inflate(LayoutInflater.from(p.context),p,false))
    override fun onBindViewHolder(h: VH, pos: Int) = h.bind(getItem(pos))
    companion object { val DIFF = object: DiffUtil.ItemCallback<Favorite>() {
        override fun areItemsTheSame(a: Favorite, b: Favorite) = a.calculatorId==b.calculatorId
        override fun areContentsTheSame(a: Favorite, b: Favorite) = a==b
    }}
}
