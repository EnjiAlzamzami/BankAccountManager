package com.example.enji

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.enji.databinding.ItemRowBinding
import kotlinx.android.synthetic.main.item_row.view.*

class RecyclerViewAdapter (private var balance : ArrayList<String>):
    RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>(){
    class ItemViewHolder (itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        //return view holder
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.apply {
            itemName.text = balance[position]
        }
    }

    override fun getItemCount() = balance.size

    fun addtext(balances: ArrayList<String>) {
        balance = balances
        notifyDataSetChanged()
    }
}
