package com.himanshu.bike_network_list.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.himanshu.bike_network_list.data.model_mapper.BikeNetworkRemoteDataMapper
import com.himanshu.bike_network_list.data.repository.BikeNetworkRepository
import com.himanshu.bike_network_list.domain.entity.BikeNetworkEntity
import com.himanshu.bike_network_list.network.BikeNetworkDto
import com.himanshu.bike_network_list.network.BikeNetworkRemoteDataSource
import com.himanshu.bike_network_list.network.Location
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class BikeNetworkRepositoryTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    lateinit var networkDataSource: BikeNetworkRemoteDataSource
    lateinit var modelMapper: BikeNetworkRemoteDataMapper

    private var testSingle: Single<List<BikeNetworkDto>>? = null

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        networkDataSource = Mockito.mock(BikeNetworkRemoteDataSource::class.java)
        modelMapper = BikeNetworkRemoteDataMapper()

        val bikeNetwork1 =
            BikeNetworkDto("1", "Eagle Network", "url", Location("City1", "Country1", 0.0, 0.0))
        val bikeNetwork2 =
            BikeNetworkDto("2", "Angle  Network", "url", Location("City1", "Country1", 0.0, 0.0))
        val bikeNetwork3 =
            BikeNetworkDto("3", "Tiger  Network", "url", Location("City1", "Country1", 0.0, 0.0))
        val bikeNetwork4 =
            BikeNetworkDto("4", "Spider  Network", "url", Location("City1", "Country1", 0.0, 0.0))

        testSingle = Single.just(listOf(bikeNetwork1, bikeNetwork2, bikeNetwork3, bikeNetwork4))
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
        Mockito.`when`(networkDataSource.getBikeNetworkList()).thenReturn(testSingle)
        val repository = BikeNetworkRepository(networkDataSource, modelMapper)
        var response: List<BikeNetworkEntity> = listOf()
        repository.getBikeNetworkList()
            .subscribeWith(object : DisposableSingleObserver<List<BikeNetworkEntity>>() {
                override fun onSuccess(value: List<BikeNetworkEntity>) {
                    response = value
                }

                override fun onError(e: Throwable?) {
                }
            })
        Assert.assertEquals(4, response.size)
        val bikeNetworkEntity = response[0]
        Assert.assertTrue(bikeNetworkEntity is BikeNetworkEntity)
    }

}