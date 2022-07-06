package com.himanshu.bike_network_list.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.himanshu.bike_network_list.domain.entity.BikeNetworkEntity
import com.himanshu.bike_network_list.domain.usecase.FilterBikeNetworkListUseCase
import com.himanshu.bike_network_list.domain.usecase.GetBikeNetworkListUseCase
import com.himanshu.bike_network_list.presentation.model.BikeNetworkUI
import com.himanshu.bike_network_list.presentation.model_mapper.BikeNetworkUIModelMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BikeNetworkListViewModel @Inject constructor(
    private val useCase: GetBikeNetworkListUseCase,
    private val filterListUseCase: FilterBikeNetworkListUseCase,
    private val modelMapper: BikeNetworkUIModelMapper,
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val _bikeNetworkList = MutableLiveData<List<BikeNetworkUI>>()
    val bikeNetworkList: LiveData<List<BikeNetworkUI>>
        get() = _bikeNetworkList

    private val _bikeNetworkListLoadError = MutableLiveData<Boolean>()
    val bikeNetworkListLoadError: LiveData<Boolean>
        get() = _bikeNetworkListLoadError

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading


    init {
        fetchBikeNetworkData()
    }

    fun fetchBikeNetworkData() {
        _loading.value = true
        disposable.add(
            useCase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(handleResponse())

        )
    }

    fun filterList(text: String) {
        _loading.value = true
        disposable.add(
            filterListUseCase(text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(handleResponse())
        )
    }

    private fun handleResponse() = object : DisposableSingleObserver<List<BikeNetworkEntity>>() {
        override fun onSuccess(value: List<BikeNetworkEntity>) {
            _bikeNetworkList.value = value.map { modelMapper.fromEntityToUIModel(it) }
            _bikeNetworkListLoadError.value = false
            _loading.value = false
        }

        override fun onError(e: Throwable?) {
            _bikeNetworkListLoadError.value = true
            _loading.value = false
        }
    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}