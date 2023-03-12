package com.paras.countrystate.country.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.paras.countrystate.R
import com.paras.countrystate.country.domain.CountryViewModel
import com.paras.countrystate.country.domain.impl.CountryViewModelImpl
import com.paras.countrystate.country.domain.model.Country
import com.paras.countrystate.country.presentation.adapter.CountryAdapter
import com.paras.countrystate.databinding.FragmentCountryBinding
import com.paras.network.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CountryFragment private constructor(): Fragment() {

    private val viewModel: CountryViewModel by viewModels<CountryViewModelImpl>()
    private lateinit var binding: FragmentCountryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCountryBinding.bind(view)
        init()
    }

    private fun init() {
        observeObservers()
        viewModel.fetchCountries()
    }

    private fun observeObservers() {
        lifecycleScope.launch {
            viewModel.countryListFlow.collectLatest {
                handleResponse(it)
            }
        }
    }

    private fun handleResponse(resource: Resource<List<Country>>) {
        when (resource) {
            is Resource.Success -> setUpUI(resource.data)
            is Resource.Error -> error(resource.error)
            is Resource.Loading -> loading()
        }
    }

    private fun loading() {
        binding.loadingView.root.visibility = View.VISIBLE
        binding.errorView.root.visibility = View.GONE
        binding.main.visibility = View.GONE
    }

    private fun error(errorMessage: String?) {
        binding.errorView.root.visibility = View.VISIBLE
        binding.loadingView.root.visibility = View.GONE
        binding.main.visibility = View.GONE
        errorMessage?.let {
            binding.errorView.errorTitle.text = it
        } ?: kotlin.run {
            binding.errorView.errorTitle.text = getString(R.string.txt_error_title)
        }
        binding.errorView.btnRetry.setOnClickListener {
            viewModel.fetchCountries()
        }
    }

    private fun setUpUI(countries: List<Country>) {
        binding.loadingView.root.visibility = View.GONE
        binding.errorView.root.visibility = View.GONE
        binding.main.visibility = View.VISIBLE
        binding.rvCountry.apply {
            adapter = CountryAdapter(requireActivity(), countries)
        }
    }

    companion object {
        fun newInstance() : CountryFragment {
            return CountryFragment()
        }
    }

}