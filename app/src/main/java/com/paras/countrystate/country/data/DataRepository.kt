package com.paras.countrystate.country.data

import com.paras.network.model.CountryResponse
import com.paras.network.model.StateResponse

interface DataRepository {

    suspend fun fetchCountryList() : CountryResponse
    suspend fun fetchStateList(countryCode: Int) : StateResponse

}