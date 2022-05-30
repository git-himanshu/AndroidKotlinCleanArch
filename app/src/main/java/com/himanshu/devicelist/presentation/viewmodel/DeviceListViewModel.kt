package com.himanshu.devicelist.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.himanshu.devicelist.domain.entity.Device
import com.himanshu.devicelist.domain.usecase.GetDeviceListUseCase
import com.himanshu.devicelist.domain.usecase.GetSortedDeviceListUseCase
import com.himanshu.devicelist.domain.usecase.Order
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DeviceListViewModel @Inject constructor(
    private val useCase: GetDeviceListUseCase,
    private val sortedUseCase: GetSortedDeviceListUseCase
) :
    ViewModel() {

    private val disposable = CompositeDisposable()

    private val _deviceList = MutableLiveData<List<Device>>()
    val deviceList: LiveData<List<Device>>
        get() = _deviceList

    private val _deviceListLoadError = MutableLiveData<Boolean>()
    val deviceListLoadError: LiveData<Boolean>
        get() = _deviceListLoadError

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading


    fun fetchDevicesData() {
        _loading.value = true
        disposable.add(
            useCase()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(handleResponse())

        )
    }

    fun fetchDeviceDataAscending() {
        _loading.value = true
        disposable.add(
            sortedUseCase(Order.ASCENDING)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(handleResponse())
        )
    }

    fun fetchDeviceDataDescending() {
        _loading.value = true
        disposable.add(
            sortedUseCase(Order.DESCENDING)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(handleResponse())
        )
    }

    private fun handleResponse() = object : DisposableSingleObserver<List<Device>>() {
        override fun onSuccess(value: List<Device>) {
            _deviceList.value = value
            _deviceListLoadError.value = false
            _loading.value = false
        }

        override fun onError(e: Throwable?) {
            _deviceListLoadError.value = true
            _loading.value = false
        }
    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}