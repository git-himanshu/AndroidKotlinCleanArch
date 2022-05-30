package com.himanshu.devicelist.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.himanshu.devicelist.R
import com.himanshu.devicelist.domain.entity.Device
import com.himanshu.devicelist.util.getProgressDrawable
import com.himanshu.devicelist.util.loadImage
import kotlinx.android.synthetic.main.row_item.view.*

class DeviceListAdapter(var devices: ArrayList<Device>) :
    RecyclerView.Adapter<DeviceListAdapter.DeviceViewHolder>() {

    fun updateMobileList(newDevices: List<Device>) {
        devices.clear();
        devices.addAll(newDevices)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DeviceViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
    )

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.bind(devices[position])
    }

    override fun getItemCount(): Int = devices.size

    class DeviceViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val imageView = view.image_view
        private val name = view.name
        private val slug = view.slug
        private val progressDrawable = getProgressDrawable(view.context)

        fun bind(device: Device) {
            imageView.loadImage(device.image, progressDrawable)
            name.text = device.name
            slug.text = device.slug
        }
    }
}