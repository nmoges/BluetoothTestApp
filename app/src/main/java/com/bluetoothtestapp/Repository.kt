package com.bluetoothtestapp

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.IntentFilter

class Repository(private val context: Context) {

    private lateinit var bluetoothManager: BluetoothManager

    private var listBluetoothDevices: MutableList<BluetoothDevice> = mutableListOf()

    init {
        initializeBluetoothAdapter()
    }

    private var bluetoothAdapter: BluetoothAdapter? = null

    var filter: IntentFilter = IntentFilter().apply {
        addAction(BluetoothDevice.ACTION_FOUND)
        addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
    }

    private fun initializeBluetoothAdapter() {
        bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter
    }

    fun addBluetoothDevice(bluetoothDevice: BluetoothDevice) {
       if (!listBluetoothDevices.contains(bluetoothDevice)) listBluetoothDevices.add(bluetoothDevice)
    }

    fun getListBluetoothDevices(): List<BluetoothDevice> {
        return listBluetoothDevices
    }

    fun getBluetoothAdapter() = bluetoothAdapter

}