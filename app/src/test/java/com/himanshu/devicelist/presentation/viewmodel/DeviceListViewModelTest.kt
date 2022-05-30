package com.himanshu.devicelist.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.himanshu.devicelist.domain.entity.Device
import com.himanshu.devicelist.domain.usecase.GetDeviceListUseCase
import com.himanshu.devicelist.domain.usecase.GetSortedDeviceListUseCase
import com.himanshu.devicelist.domain.usecase.Order
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


class DeviceListViewModelTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    lateinit var useCase: GetDeviceListUseCase
    lateinit var sortedUseCase: GetSortedDeviceListUseCase

    private var testSingle: Single<List<Device>>? = null
    private var errorSingle: Single<List<Device>>? = null

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        useCase = Mockito.mock(GetDeviceListUseCase::class.java)
        sortedUseCase = Mockito.mock(GetSortedDeviceListUseCase::class.java)
        val device = Device("IPhone 13", "iphone_13_max_pro", "url")
        val devices = listOf(device)
        testSingle = Single.just(devices)
        errorSingle = Single.error(Throwable())
    }

    @Before
    fun setupRxSchedulers() {
        var immediate = object : Scheduler() {
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
    fun fetchDevicesDataSuccess() {
        `when`(useCase()).thenReturn(testSingle)
        val deviceListViewModel = DeviceListViewModel(useCase, sortedUseCase)
        deviceListViewModel.fetchDevicesData()
        Assert.assertEquals(1, deviceListViewModel.deviceList.value?.size)
        Assert.assertEquals(false, deviceListViewModel.deviceListLoadError.value)
        Assert.assertEquals(false, deviceListViewModel.loading.value)
    }

    @Test
    fun fetchDevicesDataFailure() {
        errorSingle = Single.error(Throwable())
        `when`(useCase()).thenReturn(errorSingle)
        val deviceListViewModel = DeviceListViewModel(useCase, sortedUseCase)
        deviceListViewModel.fetchDevicesData()
        Assert.assertEquals(false, deviceListViewModel.loading.value)
        Assert.assertEquals(true, deviceListViewModel.deviceListLoadError.value)
    }

    @Test
    fun fetchDeviceDataAscendingSuccess() {
        `when`(sortedUseCase(Order.ASCENDING)).thenReturn(testSingle)
        val deviceListViewModel = DeviceListViewModel(useCase, sortedUseCase)
        deviceListViewModel.fetchDeviceDataAscending()
        Assert.assertEquals(1, deviceListViewModel.deviceList.value?.size)
        Assert.assertEquals(false, deviceListViewModel.deviceListLoadError.value)
        Assert.assertEquals(false, deviceListViewModel.loading.value)
    }

    @Test
    fun fetchDeviceDataAscendingFailure() {
        errorSingle = Single.error(Throwable())
        `when`(sortedUseCase(Order.ASCENDING)).thenReturn(errorSingle)
        val deviceListViewModel = DeviceListViewModel(useCase, sortedUseCase)
        deviceListViewModel.fetchDeviceDataAscending()
        Assert.assertEquals(false, deviceListViewModel.loading.value)
        Assert.assertEquals(true, deviceListViewModel.deviceListLoadError.value)
    }

    @Test
    fun fetchDeviceDataDescendingSuccess() {
        `when`(sortedUseCase(Order.DESCENDING)).thenReturn(testSingle)
        val deviceListViewModel = DeviceListViewModel(useCase, sortedUseCase)
        deviceListViewModel.fetchDeviceDataDescending()
        Assert.assertEquals(1, deviceListViewModel.deviceList.value?.size)
        Assert.assertEquals(false, deviceListViewModel.deviceListLoadError.value)
        Assert.assertEquals(false, deviceListViewModel.loading.value)
    }

    @Test
    fun fetchDeviceDataDescendingFailure() {
        errorSingle = Single.error(Throwable())
        `when`(sortedUseCase(Order.DESCENDING)).thenReturn(errorSingle)
        val deviceListViewModel = DeviceListViewModel(useCase, sortedUseCase)
        deviceListViewModel.fetchDeviceDataDescending()
        Assert.assertEquals(false, deviceListViewModel.loading.value)
        Assert.assertEquals(true, deviceListViewModel.deviceListLoadError.value)
    }
}