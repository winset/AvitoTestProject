package com.avito.testproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avito.testproject.repository.ItemsRepository
import com.avito.testproject.repository.ItemsRepositoryImpl
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val itemsRepository: ItemsRepository = ItemsRepositoryImpl()
) : ViewModel() {

    private val _items = MutableLiveData<List<Int>>()
    val items = _items

    init {
        getItems()
    }

    private fun getItems() {
        viewModelScope.launch {
            _items.value = itemsRepository.getStartItems()
            getNewItems()
        }
    }

    private fun getNewItems() {
        itemsRepository.getNewItem().onEach {
            insertInRandomPosition(it)
        }.launchIn(viewModelScope)
    }

    private fun insertInRandomPosition(newInt: Int) {
        val newList: MutableList<Int> = items.value as MutableList<Int>
        val position = (0..newList.size).random()
        newList.add(position,newInt)
        items.value = newList
    }

    fun onDeleteClick(number: Int) {
        val newList: MutableList<Int> = items.value as MutableList<Int>
        newList.remove(number)
        items.value = newList
        itemsRepository.addDeleteItems(number)
    }
}