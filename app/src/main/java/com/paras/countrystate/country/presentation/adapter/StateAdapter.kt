package com.paras.countrystate.country.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paras.countrystate.R
import com.paras.countrystate.country.domain.model.State
import com.paras.countrystate.databinding.ItemViewStateBinding

class StateAdapter(
    private val context: Context,
    private val states: List<State>
) : RecyclerView.Adapter<StateAdapter.StateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateViewHolder {
        return StateViewHolder(
            ItemViewStateBinding.bind(
                LayoutInflater.from(context).inflate(R.layout.item_view_state, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: StateViewHolder, position: Int) {
        holder.setData(states[position])
    }

    override fun getItemCount(): Int {
        return states.size
    }

    inner class StateViewHolder(private val binding: ItemViewStateBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setData(state: State) {
            binding.state.text = state.stateName
        }

    }

}