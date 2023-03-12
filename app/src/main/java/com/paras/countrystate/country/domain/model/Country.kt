package com.paras.countrystate.country.domain.model

import com.paras.countrystate.utils.Constants
import com.paras.network.model.CountryPojo
import java.io.Serializable

data class Country(
    val id: Int,
    val name: String,
    val code: String,
    val phoneCode: String,
    val flagImage: String
) : Serializable

fun CountryPojo.toCountry() : Country {
    return Country(
        id = this.id,
        name = this.nicename,
        code = this.iso,
        phoneCode = this.phonecode,
        flagImage = Constants.COUNTRY_IMAGE_FLAG_URL_PREFIX + this.iso.toLowerCase()
    )
}
