package com.paras.countrystate.country.domain.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonSyntaxException
import com.paras.countrystate.country.data.DataRepository
import com.paras.countrystate.country.domain.CountryDetailViewModel
import com.paras.countrystate.country.domain.model.Country
import com.paras.countrystate.country.domain.model.State
import com.paras.countrystate.country.domain.model.toState
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
class CountryDetailViewModelImpl @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel(), CountryDetailViewModel {

    private val _stateListFlow : MutableStateFlow<Resource<List<State>>> =
        MutableStateFlow(Resource.Loading())
    override val stateListFlow: StateFlow<Resource<List<State>>>
        get() = _stateListFlow

    private var country: Country? = null

    override fun fetchStates() {
        if (country == null) {
            _stateListFlow.value = Resource.Error("Country id should not be null")
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                dataRepository.fetchStateList(country?.id ?: -1).state.map {
                    it.toState()
                }.apply {
                    _stateListFlow.value = Resource.Success(this)
                }
            } catch (e: UnknownHostException) {
                _stateListFlow.value = Resource.Error("Internet not connected")
            } catch (e: JsonSyntaxException) {
                _stateListFlow.value = Resource.Success(listOf())
            } catch (e: Exception) {
                _stateListFlow.value = Resource.Error(e.message)
            }
        }
    }

    override fun setCountry(country: Country?) {
        this.country = country
    }

    override fun getCountry(): Country? {
        return country
    }
}