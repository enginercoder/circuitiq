package com.circuitiq.app.adapter
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.circuitiq.app.data.model.CalculatorCategory
import com.circuitiq.app.databinding.ItemCategoryBinding
class CategoryAdapter(
    private val items: List<CalculatorCategory>,
    private val onClick: (CalculatorCategory) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.VH>() {
    inner class VH(private val b: ItemCategoryBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(cat: CalculatorCategory) {
            b.tvIcon.text = cat.icon
            b.tvName.text = cat.name.replace(Regex("^\\S+\\s"), "")
            b.tvCount.text = ""
            b.root.setOnClickListener { onClick(cat) }
        }
    }
    override fun onCreateViewHolder(p: ViewGroup, t: Int) =
        VH(ItemCategoryBinding.inflate(LayoutInflater.from(p.context), p, false))
    override fun onBindViewHolder(h: VH, pos: Int) = h.bind(items[pos])
    override fun getItemCount() = items.size
}
