package com.bluetoothtestapp

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.bluetoothtestapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var bluetoothAdapter: BluetoothAdapter? = null

    // Contains list of Bluetooth devices
    lateinit var viewModel: ListBluetoothDevicesViewModel

    // Retrieves results
    private val requestBluetoothAction = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult())
    { result ->
        if (result.resultCode == RESULT_OK) {
            Toast.makeText(this, "Bluetooth enabled", Toast.LENGTH_SHORT).show()
        }
    }

    // Detects devices
    private val receiver = object : BroadcastReceiver() {
        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context, intent: Intent) {

            val action: String? = intent.action
            action?.let { it ->
                when (it) {
                    BluetoothDevice.ACTION_FOUND -> {
                        // Get the BluetoothDevice object and its info from the Intent
                        val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                        device?.let { viewModel.addBluetoothDevice(it) }
                        // val deviceName = device?.name
                        // val deviceHardwareAddress = device?.address // MAC Address
                        device?.let { device ->
                            Log.d("BLUETOOTH_DEVICE", "Name : ${device.name}")
                            Log.d("BLUETOOTH_DEVICE", "Address : ${device.address}")
                        }
                    }
                    BluetoothAdapter.ACTION_DISCOVERY_STARTED -> {
                        Log.d("BLUETOOTH_DEVICE", "Started")
                        // Do nothing
                    }
                    BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                        Log.d("BLUETOOTH_DEVICE", "Finished")

                        // Load list of devices in viewModel LiveData
                        viewModel.getListBluetoothDevices()
                    }
                    BluetoothAdapter.ACTION_STATE_CHANGED -> {
                        Log.d("BLUETOOTH_DEVICE", "Changed")
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeViewModel()
        bluetoothAdapter = viewModel.getBluetoothAdapter()
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
        handleButtonEnable()
        handleButtonSearch()
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(receiver, viewModel.getIntentFilter())
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }


    private fun initializeViewModel() {
        val factory = ViewModelFactory(this)
        viewModel = ViewModelProvider(this, factory).get(ListBluetoothDevicesViewModel::class.java)
    }

    /*private fun handleButtonDiscoverable() {
        binding.buttonDiscoverable.setOnClickListener {
            Log.d("CLICK_EVENT", "button discoverable")
            if (bluetoothAdapter?.isEnabled == true) {
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE).apply {
                    putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
                }
                requestBluetoothAction.launch(intent)
            }
        }
    }*/

    private fun handleButtonEnable() {
        binding.buttonEnable.setOnClickListener {
            if (bluetoothAdapter?.isEnabled == false) {
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                requestBluetoothAction.launch(intent)
            }
        }
    }

    private fun handleButtonSearch() {
        binding.buttonSearch.setOnClickListener {
            Log.d("CLICK_EVENT", "button discover")
            bluetoothAdapter?.let {
                if (it.isEnabled) {
                    if (it.isDiscovering)
                        it.cancelDiscovery()
                    else
                        it.startDiscovery()
                }
            }
        }
    }
}