package com.cr.o.cdc.mlchallenge.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cr.o.cdc.mlchallenge.R
import com.cr.o.cdc.mlchallenge.db.model.Attribute

class AttributesAdapter :
    ListAdapter<Attribute, AttributesAdapter.AttributeViewHolder>(AttributeCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttributeViewHolder =
        AttributeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_attribute,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: AttributeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class AttributeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val txt = view.findViewById<TextView>(R.id.txt_attribute)

        fun bind(attribute: Attribute) {
            txt.text = itemView.context.getString(
                R.string.attribute_separator,
                attribute.name,
                attribute.valueName
            )
        }
    }

    object AttributeCallback : DiffUtil.ItemCallback<Attribute>() {
        override fun areItemsTheSame(oldItem: Attribute, newItem: Attribute): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Attribute, newItem: Attribute): Boolean =
            oldItem == newItem

    }


}