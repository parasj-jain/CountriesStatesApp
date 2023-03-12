package com.paras.network

import com.paras.network.model.CountryResponse
import com.paras.network.model.StateRequest
import com.paras.network.model.StateResponse
import retrofit2.http.*

interface APIEndPoint {

    // region splash

    @GET(APIConstants.API_COUNTRY_LIST)
    suspend fun fetchCountries() : CountryResponse

    @Headers("content-type:application/json")
    @POST(APIConstants.API_STATE_LIST)
    suspend fun fetchStates(@Body stateRequest: StateRequest) : StateResponse

    // endregion
}