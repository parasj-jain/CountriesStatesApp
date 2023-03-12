package com.paras.countrystate.country.domain

import com.paras.countrystate.country.domain.model.Country
import com.paras.network.Resource
import kotlinx.coroutines.flow.StateFlow

interface CountryViewModel {

    val countryListFlow : StateFlow<Resource<List<Country>>>

    fun fetchCountries()

}