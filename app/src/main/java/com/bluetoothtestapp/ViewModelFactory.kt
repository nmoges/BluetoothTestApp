package com.bluetoothtestapp

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalStateException

class ViewModelFactory(private val context: Context): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListBluetoothDevicesViewModel::class.java)) {
            return ListBluetoothDevicesViewModel(context) as T
        }
        throw  IllegalStateException("Unknown ViewModel class")
    }
}