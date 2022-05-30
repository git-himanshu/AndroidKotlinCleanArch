package com.himanshu.devicelist.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.himanshu.devicelist.data.repository.DeviceRepository
import com.himanshu.devicelist.domain.entity.Device
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class GetSortedDeviceListUseCaseTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    lateinit var repository: DeviceRepository

    private var testSingle: Single<List<Device>>? = null
    private var errorSingle: Single<List<Device>>? = null

    val device1 = Device("IPhone 1", "iphone_13_max_pro", "url")
    val device2 = Device("IPhone 2", "iphone_13_max_pro", "url")
    val device3 = Device("IPhone 3", "iphone_13_max_pro", "url")
    val device4 = Device("IPhone 4", "iphone_13_max_pro", "url")
    val device5 = Device("IPhone 5", "iphone_13_max_pro", "url")

    val devices = listOf(device4, device2, device1, device3, device5)

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = Mockito.mock(DeviceRepository::class.java)

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
    fun ascendingSortedListTest() {
        val devices = listOf(device1, device2, device3, device4, device5)

        Mockito.`when`(repository.getDeviceList()).thenReturn(testSingle)
        val useCase = GetSortedDeviceListUseCase(repository)
        val result = useCase(Order.ASCENDING)

        val testObserver = TestObserver<List<Device>>()
        result.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        val listResult = testObserver.values()[0]
        assert(listResult == devices)

    }

    @Test
    fun descendingSortedListTest() {
        val devices = listOf(device5, device4, device3, device2, device1)

        Mockito.`when`(repository.getDeviceList()).thenReturn(testSingle)
        val useCase = GetSortedDeviceListUseCase(repository)
        val result = useCase(Order.DESCENDING)

        val testObserver = TestObserver<List<Device>>()
        result.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        val listResult = testObserver.values()[0]
        assert(listResult == devices)
    }
}