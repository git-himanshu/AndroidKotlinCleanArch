package com.himanshu.bike_network_list.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.himanshu.bike_network_list.domain.entity.BikeNetworkEntity
import com.himanshu.bike_network_list.domain.usecase.GetBikeNetworkListUseCase
import com.himanshu.bike_network_list.presentation.model_mapper.BikeNetworkUIModelMapper
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit


class BikeNetworkListViewModelTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    lateinit var useCase: GetBikeNetworkListUseCase
    lateinit var modelMapper: BikeNetworkUIModelMapper

    private var testSingle: Single<List<BikeNetworkEntity>>? = null
    private var testSingleFilterWithData: Single<List<BikeNetworkEntity>>? = null
    private var testSingleFilterWithNoData: Single<List<BikeNetworkEntity>>? = null
    private var errorSingle: Single<List<BikeNetworkEntity>>? = null

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        useCase = Mockito.mock(GetBikeNetworkListUseCase::class.java)
        modelMapper = BikeNetworkUIModelMapper()

        val bikeNetwork1 = BikeNetworkEntity("1", "Eagle Network", "url", "City1", "Country1")
        val bikeNetwork2 = BikeNetworkEntity("2", "Angle  Network", "url", "City1", "Country1")
        val bikeNetwork3 = BikeNetworkEntity("3", "Tiger  Network", "url", "City1", "Country1")
        val bikeNetwork4 = BikeNetworkEntity("4", "Spider  Network", "url", "City1", "Country1")

        testSingle = Single.just(listOf(bikeNetwork1, bikeNetwork2, bikeNetwork3, bikeNetwork4))
        errorSingle = Single.error(Throwable())
        testSingleFilterWithData = Single.just(listOf(bikeNetwork2))
        testSingleFilterWithNoData = Single.just(listOf())
    }

    @Before
    fun setupRxSchedulers() {
        val immediate = object : Scheduler() {
            override fun scheduleDirect(run: Runnable?, delay: Long, unit: TimeUnit?): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }
        }
        RxJavaPlugins.setInitIoSchedulerHandler { _ -> immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { _ -> immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { _ -> immediate }
        RxJavaPlugins.setSingleSchedulerHandler { _ -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> immediate }
    }

    @Test
    fun fetchBikeNetworkDataSuccess() {
        `when`(useCase()).thenReturn(testSingle)
        val bikeNetworkListViewModel = BikeNetworkListViewModel(useCase, modelMapper)
        bikeNetworkListViewModel.fetchBikeNetworkData()
        Assert.assertEquals(4, bikeNetworkListViewModel.bikeNetworkUIList.value?.size)
        Assert.assertEquals(false, bikeNetworkListViewModel.bikeNetworkListLoadError.value)
        Assert.assertEquals(false, bikeNetworkListViewModel.loading.value)
    }

    @Test
    fun fetchDevicesDataFailure() {
        errorSingle = Single.error(Throwable())
        `when`(useCase()).thenReturn(errorSingle)
        val bikeNetworkListViewModel = BikeNetworkListViewModel(useCase, modelMapper)
        bikeNetworkListViewModel.fetchBikeNetworkData()
        Assert.assertEquals(false, bikeNetworkListViewModel.loading.value)
        Assert.assertEquals(true, bikeNetworkListViewModel.bikeNetworkListLoadError.value)
    }

    @Test
    fun filterListDataFound() {
        `when`(useCase()).thenReturn(testSingle)
        val bikeNetworkListViewModel = BikeNetworkListViewModel(useCase, modelMapper)
        bikeNetworkListViewModel.fetchBikeNetworkData()
        bikeNetworkListViewModel.filterList("Ang")
        Assert.assertEquals(1, bikeNetworkListViewModel.bikeNetworkUIList.value?.size)
        Assert.assertEquals(false, bikeNetworkListViewModel.bikeNetworkListLoadError.value)
        Assert.assertEquals(false, bikeNetworkListViewModel.loading.value)
    }

    @Test
    fun filterListDataNotFound() {
        `when`(useCase()).thenReturn(testSingle)
        val bikeNetworkListViewModel = BikeNetworkListViewModel(useCase, modelMapper)
        bikeNetworkListViewModel.fetchBikeNetworkData()
        bikeNetworkListViewModel.filterList("Delhi")
        Assert.assertEquals(0, bikeNetworkListViewModel.bikeNetworkUIList.value?.size)
        Assert.assertEquals(false, bikeNetworkListViewModel.bikeNetworkListLoadError.value)
        Assert.assertEquals(false, bikeNetworkListViewModel.loading.value)
    }
}