package com.bluetoothtestapp

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bluetoothtestapp.databinding.ItemListBluetoothDevicesBinding
import com.google.android.material.textview.MaterialTextView

class ListBluetoothDevicesAdapter:
    RecyclerView.Adapter<ListBluetoothDevicesAdapter.ListBluetoothDevicesViewHolder>() {

    val listBluetoothDevices: MutableList<BluetoothDevice> = mutableListOf()

    inner class ListBluetoothDevicesViewHolder(binding: ItemListBluetoothDevicesBinding):
        RecyclerView.ViewHolder(binding.root) {
        var icon: AppCompatImageView = binding.iconItem
        var name: MaterialTextView = binding.nameDeviceItem
        var macAddress: MaterialTextView = binding.macAddressItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListBluetoothDevicesViewHolder {
        val binding = ItemListBluetoothDevicesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListBluetoothDevicesViewHolder(binding)
    }

    @SuppressLint("MissingPermission")
    override fun onBindViewHolder(holder: ListBluetoothDevicesViewHolder, position: Int) {
        holder.apply {

            name.text = if (listBluetoothDevices[position].name != null) listBluetoothDevices[position].name else "(Unknown)"
            macAddress.text = listBluetoothDevices[position].address
        }
    }

    override fun getItemCount(): Int = listBluetoothDevices.size
}