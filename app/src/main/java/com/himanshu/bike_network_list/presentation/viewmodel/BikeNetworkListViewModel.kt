package com.himanshu.bike_network_list.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.himanshu.bike_network_list.domain.entity.BikeNetworkEntity
import com.himanshu.bike_network_list.domain.usecase.GetBikeNetworkListUseCase
import com.himanshu.bike_network_list.presentation.model.BikeNetworkUI
import com.himanshu.bike_network_list.presentation.model_mapper.BikeNetworkUIModelMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class BikeNetworkListViewModel @Inject constructor(
    private val useCase: GetBikeNetworkListUseCase,
    private val modelMapper: BikeNetworkUIModelMapper,
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private var _bikeNetworkListCached = listOf<BikeNetworkUI>()

    private val _bikeNetworkUIList = MutableLiveData<List<BikeNetworkUI>>()
    val bikeNetworkUIList: LiveData<List<BikeNetworkUI>>
        get() = _bikeNetworkUIList

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
        if (text.isEmpty()) {
            _bikeNetworkUIList.value = _bikeNetworkListCached
        } else {
            val filteredList = _bikeNetworkListCached.filter {
                val networkName = it.name ?: ""
                networkName.lowercase(Locale.getDefault()).startsWith(
                    text.lowercase(
                        Locale.getDefault()
                    )
                )
            }
            _bikeNetworkUIList.value = filteredList
        }
    }

    private fun handleResponse() = object : DisposableSingleObserver<List<BikeNetworkEntity>>() {
        override fun onSuccess(value: List<BikeNetworkEntity>) {
            _bikeNetworkListCached = value.map { modelMapper.fromEntityToUIModel(it) }
            _bikeNetworkUIList.value = _bikeNetworkListCached
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