package com.cr.o.cdc.mlchallenge.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.cr.o.cdc.mlchallenge.R
import com.cr.o.cdc.mlchallenge.databinding.FragmentProductDetailBinding
import com.cr.o.cdc.mlchallenge.db.model.Attribute
import com.cr.o.cdc.mlchallenge.di.Injectable
import com.cr.o.cdc.mlchallenge.utils.loadFromUrl
import com.cr.o.cdc.mlchallenge.utils.visibleOrGone
import com.cr.o.cdc.mlchallenge.vm.ProductDetailViewModel
import javax.inject.Inject

class ProductDetailFragment : Fragment(), Injectable {

    private lateinit var binding: FragmentProductDetailBinding

    private val args: ProductDetailFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(ProductDetailViewModel::class.java)

        viewModel.loading.observe(viewLifecycleOwner, Observer(binding.progressbar::visibleOrGone))

        binding.btnRefresh.setOnClickListener {
            viewModel.refresh()
        }

        val attributesAdapter = AttributesAdapter()
        binding.recyclerAttributes.adapter = attributesAdapter

        viewModel.setProductId(args.productId)

        viewModel.product.observe(viewLifecycleOwner, Observer { r ->
            binding.btnRefresh.isEnabled = r.data != null
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

                attributesAdapter.submitList(product.getAttributesFiltered().minus(brand))
                binding.recyclerAttributes.post {
                    binding.recyclerAttributes.scrollToPosition(0)
                }
            }
        })

    }
}
