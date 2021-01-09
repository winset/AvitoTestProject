package com.avito.testproject.repository


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class ItemsRepositoryImpl : ItemsRepository {

    private val delay = 5000L
    private var itemsCount = startItems().size
    private var deleteItems = mutableListOf<Int>()

    override fun getStartItems(): List<Int> {
        return startItems()
    }

    override fun getNewItem(): Flow<Int> {
        return flow {
            while (true) {
                delay(delay)
                if (deleteItems.isEmpty()) {
                    emit(itemsCount++)
                } else {
                    emit(deleteItems.first())
                    deleteItems.removeFirst()
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun startItems(): List<Int> {
        val listItems = mutableListOf<Int>()
        for (i in 0..14) {
            listItems.add(i)
        }
        return listItems
    }

    override fun addDeleteItems(number: Int) {
        deleteItems.add(number)
    }
}