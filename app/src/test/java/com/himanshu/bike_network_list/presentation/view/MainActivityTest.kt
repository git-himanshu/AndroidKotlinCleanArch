package com.himanshu.bike_network_list.presentation.view

import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.himanshu.bike_network_list.R
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MainActivityTest {
    lateinit var mainActivity : MainActivity

    @Before
    fun init() {
        mainActivity = Robolectric.setupActivity(MainActivity::class.java)
    }

    @Test
    fun screenShouldHaveRecyclerView() {
        val recyclerView = mainActivity.findViewById<RecyclerView>(R.id.bikeNetworkList)
        assertNotNull(recyclerView);
    }

    @Test
    fun screenShouldHaveSearchBar() {
        val searchBar = mainActivity.findViewById<ActionMenuItemView>(R.id.actionSearch)
        assertNotNull(searchBar);
    }

}