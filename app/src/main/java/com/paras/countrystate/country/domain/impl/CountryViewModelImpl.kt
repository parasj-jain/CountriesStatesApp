package com.paras.countrystate.country.domain.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paras.countrystate.country.domain.CountryViewModel
import com.paras.countrystate.country.domain.model.Country
import com.paras.countrystate.country.domain.model.toCountry
import com.paras.countrystate.country.data.DataRepository
import com.paras.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class CountryViewModelImpl @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel(), CountryViewModel {

    private val _countryListFlow : MutableStateFlow<Resource<List<Country>>> =
        MutableStateFlow(Resource.Loading())
    override val countryListFlow: StateFlow<Resource<List<Country>>>
        get() = _countryListFlow

    override fun fetchCountries() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                dataRepository.fetchCountryList().country.map {
                    it.toCountry()
                }.apply {
                    _countryListFlow.value = Resource.Success(this)
                }
            } catch (e: UnknownHostException) {
                _countryListFlow.value = Resource.Error("Internet not connected")
            } catch (e: Exception) {
                _countryListFlow.value = Resource.Error(e.message)
            }
        }
    }
}