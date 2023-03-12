package com.paras.countrystate.country.domain.model

import com.paras.network.model.StatePojo

data class State(
    val stateId: Int,
    val countryId: Int,
    val stateName: String
)

fun StatePojo.toState() : State {
    return State(
        stateId = this.stateid,
        countryId = this.countryid,
        stateName = this.statename
    )
}
