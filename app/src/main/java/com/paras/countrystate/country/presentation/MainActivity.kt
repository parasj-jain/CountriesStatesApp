package com.paras.countrystate.country.presentation

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.paras.countrystate.R
import com.paras.countrystate.country.domain.CountrySelectedListener
import com.paras.countrystate.country.domain.model.Country
import com.paras.countrystate.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity(), CountrySelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        launchCountryFragment()
    }

    private fun launchCountryFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, CountryFragment.newInstance())
        fragmentTransaction.commit()
    }

    private fun launchCountryDetailFragment(country: Country) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.container, CountryDetailFragment.newInstance(country))
        fragmentTransaction.addToBackStack("country_detail")
        fragmentTransaction.commit()
    }

    override fun onCountrySelected(country: Country) {
        launchCountryDetailFragment(country)
    }
}