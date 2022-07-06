package com.himanshu.bike_network_list.presentation.viewmodel.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.himanshu.bike_network_list.presentation.viewmodel.BikeNetworkListViewModel
import javax.inject.Inject
import javax.inject.Provider

class BikeNetworkListViewModelFactory @Inject constructor(private val myViewModelProvider: Provider<BikeNetworkListViewModel>) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return myViewModelProvider.get() as T
    }

}