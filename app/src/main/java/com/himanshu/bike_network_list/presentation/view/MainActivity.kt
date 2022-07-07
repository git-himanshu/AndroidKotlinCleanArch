package com.himanshu.bike_network_list.presentation.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.himanshu.bike_network_list.R
import com.himanshu.bike_network_list.app.BikeNetworksApp
import com.himanshu.bike_network_list.presentation.viewmodel.BikeNetworkListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var viewModel: BikeNetworkListViewModel
    private val bikeNetworkListAdapter = BikeNetworkListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val app = application as BikeNetworksApp
        app.appComponent.inject(this)

        viewModel = ViewModelProviders.of(this, factory).get(BikeNetworkListViewModel::class.java)

        bikeNetworkList.apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(context)
            adapter = bikeNetworkListAdapter
        }
        observeViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchItem: MenuItem = menu!!.findItem(R.id.actionSearch)
        val searchView: SearchView = searchItem.getActionView() as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty())
                    viewModel.filterList(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filterList(newText!!)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }


    private fun observeViewModel() {
        viewModel.bikeNetworkUIList.observe(this) { networks ->
            networks.let {
                bikeNetworkListAdapter.updateMobileList(it)
                bikeNetworkList.visibility = View.VISIBLE
            }
        }

        viewModel.bikeNetworkListLoadError.observe(this) { isError ->
            isError.let { listError.visibility = if (it) View.VISIBLE else View.GONE }
        }

        viewModel.loading.observe(this) { isLoading ->
            isLoading.let {
                if (it) {
                    loadingView.visibility = View.VISIBLE
                    bikeNetworkList.visibility = View.GONE
                    listError.visibility = View.GONE
                } else {
                    loadingView.visibility = View.GONE
                }
            }
        }
    }
}