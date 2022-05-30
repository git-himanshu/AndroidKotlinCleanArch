package com.himanshu.devicelist.di

import com.himanshu.devicelist.app.DeviceInformationApp
import com.himanshu.devicelist.network.di.NetworkModule
import com.himanshu.devicelist.presentation.view.MainActivity
import com.himanshu.devicelist.presentation.viewmodel.di.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(application: DeviceInformationApp)
    fun inject(activity: MainActivity)
}