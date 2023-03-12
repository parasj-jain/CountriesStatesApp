package com.paras.countrystate.country.domain

import com.paras.countrystate.country.domain.model.Country
import com.paras.countrystate.country.domain.model.State
import com.paras.network.Resource
import kotlinx.coroutines.flow.StateFlow

interface CountryDetailViewModel {

    val stateListFlow : StateFlow<Resource<List<State>>>

    fun fetchStates()

    fun setCountry(country: Country?)

    fun getCountry() : Country?

}