package com.himanshu.bike_network_list.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.himanshu.bike_network_list.data.repository.BikeNetworkRepository
import com.himanshu.bike_network_list.domain.entity.BikeNetworkEntity
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

class GetBikeNetworkListUseCaseTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    lateinit var repository: BikeNetworkRepository

    private var testSingle: Single<List<BikeNetworkEntity>>? = null
    private var errorSingle: Single<List<BikeNetworkEntity>>? = null

    val bikeNetwork1 = BikeNetworkEntity("1", "Network 1", "url", "City1", "Country1")
    val bikeNetwork2 = BikeNetworkEntity("2", "Network 2", "url", "City2", "Country2")
    val bikeNetwork3 = BikeNetworkEntity("3", "Network 3", "url", "City3", "Country3")
    val bikeNetwork4 = BikeNetworkEntity("4", "Network 4", "url", "City4", "Country4")
    val bikeNetwork5 = BikeNetworkEntity("5", "Network 5", "url", "City5", "Country5")

    val networks = listOf(bikeNetwork4, bikeNetwork2, bikeNetwork1, bikeNetwork3, bikeNetwork5)

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = Mockito.mock(BikeNetworkRepository::class.java)

        testSingle = Single.just(networks)
        errorSingle = Single.error(Throwable())
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
    fun getListTest() {
        Mockito.`when`(repository.getBikeNetworkList()).thenReturn(testSingle)
        val useCase = GetBikeNetworkListUseCase(repository)
        val result = useCase()

        val testObserver = TestObserver<List<BikeNetworkEntity>>()
        result.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        val listResult = testObserver.values()[0]
        assert(listResult == networks)
    }
}