package com.makhmutov.internshipvkmarket.domain.respository

import com.makhmutov.internshipvkmarket.domain.entities.RequestMarketItemListResult
import com.makhmutov.internshipvkmarket.domain.entities.RequestOneMarketItemResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * Реопозиторий для работы с главной(товарами)
 */
interface MarketRepository {

    /**
     * Поток данных со списком товаров
     */
    val marketItemsFlow: StateFlow<RequestMarketItemListResult>

    /**
     * Метод для получения потока данных с товаром с определенными id
     */
    fun getOneMarketItemByIdFlow(id: Int): Flow<RequestOneMarketItemResult>

    /**
     * Метод для запроса данных при пагинации
     */
    suspend fun requestMarketItems()

    /**
     * Метод для запроса данных о товарах определенной категории
     */
    suspend fun requestByCategoryMarketItems(category: String)

    /**
     * Метод для получения потока с категориями
     */
    fun getCategories(): StateFlow<List<String>>

    /**
     * Метод для поиска товара
     */
    suspend fun searchMarketItems(request: String)

}