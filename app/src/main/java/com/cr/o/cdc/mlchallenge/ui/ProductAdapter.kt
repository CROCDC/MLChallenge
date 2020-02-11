package com.cr.o.cdc.mlchallenge.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cr.o.cdc.mlchallenge.R
import com.cr.o.cdc.mlchallenge.databinding.ListItemProductBinding
import com.cr.o.cdc.mlchallenge.db.model.Product
import com.cr.o.cdc.mlchallenge.utils.loadFromUrl
import com.cr.o.cdc.mlchallenge.utils.visibleOrGone

class ProductAdapter : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder =
        ProductViewHolder(
            ListItemProductBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.itemView.setOnClickListener {
            it.findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToProductDetailFragment(product.id)
            )
        }
        holder.bind(product)
    }


    class ProductViewHolder(private val binding: ListItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.img.loadFromUrl(product.thumbnail)
            binding.txtTitle.text = product.title
            binding.txtPrice.text = product.price.toString()
            binding.txtShippingFree.visibleOrGone(product.shipping.free)

            product.installments?.quantity?.let {
                binding.txtInstallments.visibility = View.VISIBLE
                binding.txtInstallments.text = itemView.context.getString(R.string.installments, it)
            } ?: kotlin.run {
                binding.txtInstallments.visibility = View.GONE
            }
        }
    }

    object ProductCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
            oldItem.id == newItem.id

    }
}
