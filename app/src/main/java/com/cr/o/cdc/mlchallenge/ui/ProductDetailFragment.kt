package com.cr.o.cdc.mlchallenge.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.cr.o.cdc.mlchallenge.R
import com.cr.o.cdc.mlchallenge.databinding.FragmentProductDetailBinding
import com.cr.o.cdc.mlchallenge.db.model.Attribute
import com.cr.o.cdc.mlchallenge.utils.loadFromUrl
import com.cr.o.cdc.mlchallenge.utils.visibleOrGone
import com.cr.o.cdc.mlchallenge.vm.ProductDetailViewModel

class ProductDetailFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailBinding

    val args: ProductDetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProviders.of(this).get(ProductDetailViewModel::class.java)


        viewModel.loading.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visibleOrGone(it)
        })


        val attributesAdapter = AttributesAdapter()
        binding.recyclerAttributes.adapter = attributesAdapter

        viewModel.item(args.productId).observe(viewLifecycleOwner, Observer { r ->
            r.data?.let { product ->
                binding.img.loadFromUrl(product.thumbnail)
                binding.txtSoldQuantity.text = getString(R.string.cant_sold, product.soldQuantity)
                binding.txtTitle.text = product.title

                val brand = product.attributes.find { it.id == Attribute.ID_BRAND }

                brand?.valueName?.let {
                    binding.txtBrand.text = getString(R.string.brand, it)
                }

                binding.txtPrice.text = product.price.toString()

                binding.txtAttributes.visibility = View.VISIBLE
                attributesAdapter.submitList(product.attributes.minus(brand))
            }
        })

    }
}
