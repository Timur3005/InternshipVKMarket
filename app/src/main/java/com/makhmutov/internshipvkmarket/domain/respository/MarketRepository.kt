package com.makhmutov.internshipvkmarket.domain.respository

import com.makhmutov.internshipvkmarket.domain.entities.RequestMarketItemListResult
import com.makhmutov.internshipvkmarket.domain.entities.RequestOneMarketItemResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MarketRepository {

    val marketItemsFlow: StateFlow<RequestMarketItemListResult>
    fun getOneMarketItemByIdFlow(id: Int): Flow<RequestOneMarketItemResult>
    suspend fun requestMarketItems()
    suspend fun requestByCategoryMarketItems(category: String)
    fun getCategories(): StateFlow<List<String>>
    suspend fun searchMarketItems(request: String)

}