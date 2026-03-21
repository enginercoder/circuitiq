package com.circuitiq.app.ui.history

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.circuitiq.app.adapter.HistoryAdapter
import com.circuitiq.app.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {
    private var _b: FragmentHistoryBinding? = null
    private val b get() = _b!!
    private val vm: HistoryViewModel by viewModels()

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = FragmentHistoryBinding.inflate(i,c,false); return b.root
    }

    override fun onViewCreated(v: View, s: Bundle?) {
        super.onViewCreated(v, s)
        val adapter = HistoryAdapter { entry -> vm.delete(entry.id) }
        b.rv.layoutManager = LinearLayoutManager(requireContext())
        b.rv.adapter = adapter
        vm.history.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            b.tvEmpty.visibility = if(it.isEmpty()) View.VISIBLE else View.GONE
            b.tvCount.text = "${it.size} calculations"
        }
        b.btnClear.setOnClickListener {
            AlertDialog.Builder(requireContext()).setTitle("Clear History").setMessage("Delete all calculation history?")
                .setPositiveButton("Clear") { _,_ -> vm.clearAll(); Toast.makeText(requireContext(),"History cleared",Toast.LENGTH_SHORT).show() }
                .setNegativeButton("Cancel",null).show()
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _b=null }
}
