package com.paras.countrystate.country.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.paras.countrystate.R
import com.paras.countrystate.country.domain.CountryDetailViewModel
import com.paras.countrystate.country.domain.impl.CountryDetailViewModelImpl
import com.paras.countrystate.country.domain.model.Country
import com.paras.countrystate.country.domain.model.State
import com.paras.countrystate.country.presentation.adapter.StateAdapter
import com.paras.countrystate.databinding.FragmentCountryDetailBinding
import com.paras.countrystate.utils.Constants
import com.paras.countrystate.utils.serializable
import com.paras.network.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CountryDetailFragment private constructor(): Fragment() {

    private val viewModel: CountryDetailViewModel by viewModels<CountryDetailViewModelImpl>()
    private lateinit var binding: FragmentCountryDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_country_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCountryDetailBinding.bind(view)
        init()
    }

    private fun init() {
        observeObservers()
        arguments?.serializable<Country>(Constants.INTENT_EXTRA_COUNTRY).let {
            viewModel.setCountry(country = it)
        }
        viewModel.fetchStates()
    }

    private fun observeObservers() {
        lifecycleScope.launch {
            viewModel.stateListFlow.collectLatest {
                handleResponse(it)
            }
        }
    }

    private fun handleResponse(resource: Resource<List<State>>) {
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
            viewModel.fetchStates()
        }
    }

    private fun setUpUI(states: List<State>) {
        binding.loadingView.root.visibility = View.GONE
        binding.errorView.root.visibility = View.GONE
        binding.main.visibility = View.VISIBLE
        binding.screenTitle.text = viewModel.getCountry()?.name
        Glide.with(requireContext())
            .load(viewModel.getCountry()?.flagImage)
            .into(binding.countryFlagImage)
        if (states.isEmpty()) {
            binding.noState.visibility = View.VISIBLE
            binding.rvStates.visibility = View.GONE
        } else {
            binding.noState.visibility = View.GONE
            binding.rvStates.visibility = View.VISIBLE
            binding.rvStates.apply {
                adapter = StateAdapter(requireContext(), states)
            }
        }
    }

    companion object {
        fun newInstance(country: Country) : CountryDetailFragment {
            val bundle = Bundle().apply {
                putSerializable(Constants.INTENT_EXTRA_COUNTRY, country)
            }
            return CountryDetailFragment().apply {
                arguments = bundle
            }
        }
    }

}