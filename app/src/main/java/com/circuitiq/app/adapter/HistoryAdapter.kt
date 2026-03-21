package com.circuitiq.app.adapter

import android.view.*
import androidx.recyclerview.widget.*
import com.circuitiq.app.data.model.HistoryEntry
import com.circuitiq.app.databinding.ItemHistoryBinding
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter(private val onDelete: (HistoryEntry) -> Unit) : ListAdapter<HistoryEntry, HistoryAdapter.VH>(DIFF) {
    inner class VH(private val b: ItemHistoryBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(e: HistoryEntry) {
            b.tvName.text = e.calculatorName
            b.tvResult.text = e.result
            b.tvFormula.text = e.formula
            b.tvTime.text = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault()).format(Date(e.timestamp))
            b.btnDelete.setOnClickListener { onDelete(e) }
        }
    }
    override fun onCreateViewHolder(p: ViewGroup, t: Int) = VH(ItemHistoryBinding.inflate(LayoutInflater.from(p.context),p,false))
    override fun onBindViewHolder(h: VH, pos: Int) = h.bind(getItem(pos))
    companion object { val DIFF = object: DiffUtil.ItemCallback<HistoryEntry>() {
        override fun areItemsTheSame(a: HistoryEntry, b: HistoryEntry) = a.id==b.id
        override fun areContentsTheSame(a: HistoryEntry, b: HistoryEntry) = a==b
    }}
}
