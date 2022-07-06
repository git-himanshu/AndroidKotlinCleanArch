package com.himanshu.bike_network_list.di

import com.himanshu.bike_network_list.app.BikeNetworksApp
import com.himanshu.bike_network_list.network.di.NetworkModule
import com.himanshu.bike_network_list.presentation.view.MainActivity
import com.himanshu.bike_network_list.presentation.viewmodel.di.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(application: BikeNetworksApp)
    fun inject(activity: MainActivity)
}