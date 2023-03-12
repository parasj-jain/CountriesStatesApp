package com.paras.countrystate.country.domain

import com.paras.countrystate.country.domain.model.Country

interface CountrySelectedListener {

    fun onCountrySelected(country: Country)

}