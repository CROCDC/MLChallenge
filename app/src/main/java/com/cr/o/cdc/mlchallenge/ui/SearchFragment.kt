package com.cr.o.cdc.mlchallenge.ui


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.cr.o.cdc.mlchallenge.databinding.FragmentSearchBinding
import com.cr.o.cdc.mlchallenge.di.Injectable
import com.cr.o.cdc.mlchallenge.utils.visibleOrGone
import com.cr.o.cdc.mlchallenge.vm.SearchViewModel
import javax.inject.Inject

class SearchFragment : Fragment(), Injectable {

    private lateinit var binding: FragmentSearchBinding

    private lateinit var adapter: ProductAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        adapter = ProductAdapter()
        binding.recycler.adapter = adapter
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.setSearch(query)
                (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
                    ?.hideSoftInputFromWindow(binding.root.windowToken, 0)
                return true
            }

            override fun onQueryTextChange(newText: String?) = false

        })

        viewModel.loading.observe(viewLifecycleOwner, Observer(binding.progressbar::visibleOrGone))

        viewModel.products.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it.data)
        })
    }
}
