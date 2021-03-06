package com.himanshu.bike_network_list.presentation.viewmodel.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindsViewModelFactory(factory: BikeNetworkListViewModelFactory): ViewModelProvider.Factory
}