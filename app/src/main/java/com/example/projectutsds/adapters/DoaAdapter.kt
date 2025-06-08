package com.example.projectutsds.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectutsds.R
import com.example.projectutsds.models.DoaItem

class DoaAdapter(
    private var doaList: List<DoaItem>,
    private val onItemClick: (DoaItem) -> Unit
) : RecyclerView.Adapter<DoaAdapter.DoaViewHolder>() {

    class DoaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textDoaName: TextView = itemView.findViewById(R.id.textDoaName)
        val textDoaId: TextView = itemView.findViewById(R.id.textDoaId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_doa, parent, false)
        return DoaViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoaViewHolder, position: Int) {
        val doa = doaList[position]
        holder.textDoaName.text = doa.doa
        holder.textDoaId.text = "ID: ${doa.id}"

        holder.itemView.setOnClickListener {
            onItemClick(doa)
        }
    }

    override fun getItemCount(): Int = doaList.size

    fun updateData(newDoaList: List<DoaItem>) {
        doaList = newDoaList
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        val filteredList = if (query.isEmpty()) {
            doaList
        } else {
            doaList.filter { doa ->
                doa.doa.contains(query, ignoreCase = true)
            }
        }
        updateData(filteredList)
    }
}