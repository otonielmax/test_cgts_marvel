package com.example.testcgts.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.testcgts.R
import com.example.testcgts.models.ItemCommon


class ItemHorizontalAdapter(
    private var items: List<ItemCommon>
) : RecyclerView.Adapter<ItemHorizontalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHorizontalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_card_details,
            parent,
            false
        )
        return ItemHorizontalViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemHorizontalViewHolder, position: Int) {
        return holder.bind(items[position])
    }

    fun updateData(data: List<ItemCommon>) {
        items = data
        notifyDataSetChanged()
    }

}

class ItemHorizontalViewHolder(item: View) : RecyclerView.ViewHolder(item) {

    private var title: TextView = item.findViewById(R.id.title)

    fun bind(item: ItemCommon) = with(itemView) {
        title.text = item.name

        setOnClickListener {
            /*
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.resourceURI))
            itemView.context.startActivity(browserIntent)
             */
        }
    }
}