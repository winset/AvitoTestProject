package com.avito.testproject.ui

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avito.testproject.ui.adapter.ItemsAdapter
import com.avito.testproject.viewmodel.MainViewModel
import com.avito.testproject.R

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private val itemsAdapter = ItemsAdapter(::onItemClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        recyclerInit()
        loadItems()
    }

    private fun recyclerInit() {
        val recycler = findViewById<RecyclerView>(R.id.recycler)
        recycler.adapter = itemsAdapter
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            recycler.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        else
            recycler.layoutManager = GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false)
    }

    private fun loadItems() {
        mainViewModel.items.observe(this, Observer {
            itemsAdapter.updateData(it)
        })
    }

    private fun onItemClick(number: Int) {
        mainViewModel.onDeleteClick(number)
    }
}