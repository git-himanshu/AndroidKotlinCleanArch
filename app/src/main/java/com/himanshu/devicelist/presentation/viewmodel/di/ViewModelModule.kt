package com.himanshu.devicelist.presentation.viewmodel.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindsViewModelFactory(factory: DeviceListViewModelFactory): ViewModelProvider.Factory
}