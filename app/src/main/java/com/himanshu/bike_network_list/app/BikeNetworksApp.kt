package com.himanshu.bike_network_list.app

import android.app.Application
import com.himanshu.bike_network_list.di.ApplicationComponent
import com.himanshu.bike_network_list.di.DaggerApplicationComponent

class BikeNetworksApp : Application() {
    lateinit var appComponent: ApplicationComponent
    override fun onCreate() {
        appComponent = DaggerApplicationComponent.create()
        super.onCreate()
    }
}