package com.himanshu.devicelist.presentation.viewmodel.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.himanshu.devicelist.presentation.viewmodel.DeviceListViewModel
import javax.inject.Inject
import javax.inject.Provider

class DeviceListViewModelFactory @Inject constructor(private val myViewModelProvider: Provider<DeviceListViewModel>) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return myViewModelProvider.get() as T
    }

}