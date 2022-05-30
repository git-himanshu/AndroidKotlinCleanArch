package com.himanshu.devicelist.app

import android.app.Application
import com.himanshu.devicelist.di.ApplicationComponent
import com.himanshu.devicelist.di.DaggerApplicationComponent

class DeviceInformationApp : Application() {
    lateinit var appComponent: ApplicationComponent
    override fun onCreate() {
        appComponent = DaggerApplicationComponent.create()
        super.onCreate()
    }
}