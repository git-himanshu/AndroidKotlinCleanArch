//package com.himanshu.bike_network_list.presentation.viewmodel
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import com.himanshu.bike_network_list.domain.entity.BikeNetwork
//import com.himanshu.bike_network_list.domain.usecase.FilterBikeNetworkListUseCase
//import com.himanshu.bike_network_list.domain.usecase.GetBikeNetworkListUseCase
//import io.reactivex.Scheduler
//import io.reactivex.Single
//import io.reactivex.android.plugins.RxAndroidPlugins
//import io.reactivex.disposables.Disposable
//import io.reactivex.internal.schedulers.ExecutorScheduler
//import io.reactivex.plugins.RxJavaPlugins
//import org.junit.Assert
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.mockito.Mockito
//import org.mockito.Mockito.`when`
//import org.mockito.MockitoAnnotations
//import java.util.concurrent.Executor
//import java.util.concurrent.TimeUnit
//
//
//class BikeNetworkListViewModelTest {
//    @get:Rule
//    var rule = InstantTaskExecutorRule()
//
//    lateinit var useCase: GetBikeNetworkListUseCase
//    lateinit var sortedUseCase: FilterBikeNetworkListUseCase
//
//    private var testSingle: Single<List<BikeNetwork>>? = null
//    private var errorSingle: Single<List<BikeNetwork>>? = null
//
//    @Before
//    fun setup() {
//        MockitoAnnotations.openMocks(this)
//        useCase = Mockito.mock(GetBikeNetworkListUseCase::class.java)
//        sortedUseCase = Mockito.mock(FilterBikeNetworkListUseCase::class.java)
//        val bikeNetwork = BikeNetwork("1", "Network 1", "url", "City1", "Country1")
//        val devices = listOf(bikeNetwork)
//        testSingle = Single.just(devices)
//        errorSingle = Single.error(Throwable())
//    }
//
//    @Before
//    fun setupRxSchedulers() {
//        var immediate = object : Scheduler() {
//            override fun scheduleDirect(run: Runnable?, delay: Long, unit: TimeUnit?): Disposable {
//                return super.scheduleDirect(run, 0, unit)
//            }
//
//            override fun createWorker(): Worker {
//                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
//            }
//        }
//        RxJavaPlugins.setInitIoSchedulerHandler { _ -> immediate }
//        RxJavaPlugins.setInitComputationSchedulerHandler { _ -> immediate }
//        RxJavaPlugins.setInitNewThreadSchedulerHandler { _ -> immediate }
//        RxJavaPlugins.setSingleSchedulerHandler { _ -> immediate }
//        RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> immediate }
//    }
//
//    @Test
//    fun fetchDevicesDataSuccess() {
//        `when`(useCase()).thenReturn(testSingle)
//        val bikeNetworkListViewModel = BikeNetworkListViewModel(useCase, sortedUseCase)
//        bikeNetworkListViewModel.fetchBikeNetworlData()
//        Assert.assertEquals(1, bikeNetworkListViewModel.bikeNetworkList.value?.size)
//        Assert.assertEquals(false, bikeNetworkListViewModel.bikeNetworkListLoadError.value)
//        Assert.assertEquals(false, bikeNetworkListViewModel.loading.value)
//    }
//
//    @Test
//    fun fetchDevicesDataFailure() {
//        errorSingle = Single.error(Throwable())
//        `when`(useCase()).thenReturn(errorSingle)
//        val bikeNetworkListViewModel = BikeNetworkListViewModel(useCase, sortedUseCase)
//        bikeNetworkListViewModel.fetchBikeNetworlData()
//        Assert.assertEquals(false, bikeNetworkListViewModel.loading.value)
//        Assert.assertEquals(true, bikeNetworkListViewModel.bikeNetworkListLoadError.value)
//    }
//
//    @Test
//    fun fetchDeviceDataAscendingSuccess() {
//        `when`(sortedUseCase(Order.ASCENDING)).thenReturn(testSingle)
//        val bikeNetworkListViewModel = BikeNetworkListViewModel(useCase, sortedUseCase)
//        bikeNetworkListViewModel.fetchBikeNetworkDataAscending()
//        Assert.assertEquals(1, bikeNetworkListViewModel.bikeNetworkList.value?.size)
//        Assert.assertEquals(false, bikeNetworkListViewModel.bikeNetworkListLoadError.value)
//        Assert.assertEquals(false, bikeNetworkListViewModel.loading.value)
//    }
//
//    @Test
//    fun fetchDeviceDataAscendingFailure() {
//        errorSingle = Single.error(Throwable())
//        `when`(sortedUseCase(Order.ASCENDING)).thenReturn(errorSingle)
//        val bikeNetworkListViewModel = BikeNetworkListViewModel(useCase, sortedUseCase)
//        bikeNetworkListViewModel.fetchBikeNetworkDataAscending()
//        Assert.assertEquals(false, bikeNetworkListViewModel.loading.value)
//        Assert.assertEquals(true, bikeNetworkListViewModel.bikeNetworkListLoadError.value)
//    }
//
//    @Test
//    fun fetchDeviceDataDescendingSuccess() {
//        `when`(sortedUseCase(Order.DESCENDING)).thenReturn(testSingle)
//        val bikeNetworkListViewModel = BikeNetworkListViewModel(useCase, sortedUseCase)
//        bikeNetworkListViewModel.fetchBikeNetworkDataDescending()
//        Assert.assertEquals(1, bikeNetworkListViewModel.bikeNetworkList.value?.size)
//        Assert.assertEquals(false, bikeNetworkListViewModel.bikeNetworkListLoadError.value)
//        Assert.assertEquals(false, bikeNetworkListViewModel.loading.value)
//    }
//
//    @Test
//    fun fetchDeviceDataDescendingFailure() {
//        errorSingle = Single.error(Throwable())
//        `when`(sortedUseCase(Order.DESCENDING)).thenReturn(errorSingle)
//        val bikeNetworkListViewModel = BikeNetworkListViewModel(useCase, sortedUseCase)
//        bikeNetworkListViewModel.fetchBikeNetworkDataDescending()
//        Assert.assertEquals(false, bikeNetworkListViewModel.loading.value)
//        Assert.assertEquals(true, bikeNetworkListViewModel.bikeNetworkListLoadError.value)
//    }
//}