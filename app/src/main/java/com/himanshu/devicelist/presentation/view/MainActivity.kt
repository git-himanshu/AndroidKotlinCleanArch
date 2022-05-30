package com.himanshu.devicelist.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.himanshu.devicelist.app.DeviceInformationApp
import com.himanshu.devicelist.R
import com.himanshu.devicelist.presentation.viewmodel.DeviceListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var viewModel: DeviceListViewModel
    private val deviceListAdapter = DeviceListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val app = application as DeviceInformationApp
        app.appComponent.inject(this)

        viewModel = ViewModelProviders.of(this, factory).get(DeviceListViewModel::class.java)
        viewModel.fetchDevicesData()

        deviceList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = deviceListAdapter
        }

        swipe_refresh_layout.setOnRefreshListener {
            swipe_refresh_layout.isRefreshing = false
            getData(radioGroup.checkedRadioButtonId)
        }

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            getData(checkedId)
        }

        observeViewModel()
    }

    private fun getData(checkedId: Int) {
        when (checkedId) {
            R.id.rbDefault -> viewModel.fetchDevicesData()
            R.id.rbAscending -> viewModel.fetchDeviceDataAscending()
            R.id.rbDescending -> viewModel.fetchDeviceDataDescending()
        }
    }

    fun observeViewModel() {
        viewModel.deviceList.observe(this, { devices ->
            devices.let {
                deviceListAdapter.updateMobileList(it)
                deviceList.visibility = View.VISIBLE
            }
        })

        viewModel.deviceListLoadError.observe(this, { isError ->
            isError.let { listError.visibility = if (it) View.VISIBLE else View.GONE }
        })

        viewModel.loading.observe(this, { isLoading ->
            isLoading.let {
                if (it) {
                    loadingView.visibility = View.VISIBLE
                    deviceList.visibility = View.GONE
                    listError.visibility = View.GONE
                } else {
                    loadingView.visibility = View.GONE
                }
            }
        })
    }
}