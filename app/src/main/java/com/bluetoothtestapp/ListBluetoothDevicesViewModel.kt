package com.bluetoothtestapp

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.IntentFilter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListBluetoothDevicesViewModel(context: Context): ViewModel() {

    private val repository = Repository(context)

    private val _listBluetoothDevices: MutableLiveData<List<BluetoothDevice>> = MutableLiveData()
    val listBluetoothDevices: LiveData<List<BluetoothDevice>>
        get() = _listBluetoothDevices

    fun getIntentFilter(): IntentFilter = repository.filter

    fun addBluetoothDevice(bluetoothDevice: BluetoothDevice) {
        repository.addBluetoothDevice(bluetoothDevice)
    }

    fun getListBluetoothDevices() {
        _listBluetoothDevices.postValue(repository.getListBluetoothDevices())
    }

    fun getBluetoothAdapter() = repository.getBluetoothAdapter()
}