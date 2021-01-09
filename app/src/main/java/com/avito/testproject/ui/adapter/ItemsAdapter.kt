package com.avito.testproject.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.avito.testproject.R


open class ItemsAdapter(private val onItemClick: (Int) -> Unit) :
    RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    private val items = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.create(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateData(items: List<Int>) {
        val diffResult = DiffUtil.calculateDiff(ItemsDiffUtilCallback(this.items, items))
        this.items.clear()
        this.items.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    class ItemViewHolder(itemView: View, val onItemClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(number: Int) {
            val numberTextView = itemView.findViewById<TextView>(R.id.element_number)
            val deleteButton = itemView.findViewById<Button>(R.id.element_delete)
            numberTextView.text = number.toString()
            deleteButton.setOnClickListener(View.OnClickListener {
                onItemClick(number)
            })
        }

        companion object {
            fun create(parent: ViewGroup, onItemClick: (Int) -> Unit): ItemViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_item, parent, false)

                return ItemViewHolder(view, onItemClick)
            }
        }
    }
}