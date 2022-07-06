package com.himanshu.bike_network_list.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.himanshu.bike_network_list.R
import com.himanshu.bike_network_list.domain.entity.BikeNetworkEntity
import com.himanshu.bike_network_list.presentation.model.BikeNetworkUI
import kotlinx.android.synthetic.main.row_item.view.*

class BikeNetworkListAdapter(var bikeNetworks: ArrayList<BikeNetworkUI>) :
    RecyclerView.Adapter<BikeNetworkListAdapter.DeviceViewHolder>() {

    fun updateMobileList(newBikeNetworks: List<BikeNetworkUI>) {
        bikeNetworks.clear();
        bikeNetworks.addAll(newBikeNetworks)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DeviceViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
    )

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.bind(bikeNetworks[position])
    }

    override fun getItemCount(): Int = bikeNetworks.size

    class DeviceViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val name = view.name
        private val city = view.city
        private val country = view.country

        fun bind(bikeNetwork: BikeNetworkUI) {
            name.text = bikeNetwork.name
            city.text = bikeNetwork.city
            country.text = bikeNetwork.country
        }
    }
}