package com.bluetoothtestapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bluetoothtestapp.databinding.FragmentListBluetoothDevicesBinding as Binding
import com.bluetoothtestapp.MainActivity

/**
 * Fragment child displaying the list of detected Bluetooth devices.
 */
class ListBluetoothDevicesFragment : BaseFragment<Binding>() {

    override val layoutId: Int = R.layout.fragment_list_bluetooth_devices

    private lateinit var mainActivity: MainActivity

    private fun handleProgressBarDisplay(visibility: Int) {
        binding.progressBar.visibility = visibility
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as MainActivity
        initializeRecyclerView()
        initializeObserver()
    }

    @SuppressLint("MissingPermission")
    private fun initializeObserver() {
        mainActivity.viewModel.listBluetoothDevices.observe(viewLifecycleOwner) { newlistBluetoothDevices ->
            handleProgressBarDisplay(View.INVISIBLE)
            (binding.recyclerViewBluetoothDevices.adapter as ListBluetoothDevicesAdapter).apply {
                listBluetoothDevices.apply {
                    clear()
                    addAll(newlistBluetoothDevices)
                }
                notifyDataSetChanged()
            }
        }
    }

    private fun initializeRecyclerView() {
        binding.recyclerViewBluetoothDevices.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = ListBluetoothDevicesAdapter()
        }
    }
}