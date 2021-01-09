package com.avito.testproject.repository

import kotlinx.coroutines.flow.Flow

interface ItemsRepository {
    fun getStartItems(): List<Int>
    fun getNewItem(): Flow<Int>
    fun addDeleteItems(number: Int)
}