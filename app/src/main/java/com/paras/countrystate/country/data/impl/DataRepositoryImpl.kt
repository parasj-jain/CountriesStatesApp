package com.paras.countrystate.country.data.impl

import com.paras.network.APIEndPoint
import com.paras.network.model.CountryResponse
import com.paras.network.model.StateRequest
import com.paras.network.model.StateResponse
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(
    private val apiEndPoint: APIEndPoint
): com.paras.countrystate.country.data.DataRepository {
    override suspend fun fetchCountryList(): CountryResponse {
        return apiEndPoint.fetchCountries()
    }

    override suspend fun fetchStateList(countryCode: Int): StateResponse {
        return apiEndPoint.fetchStates(stateRequest = StateRequest(countryCode))
    }
}