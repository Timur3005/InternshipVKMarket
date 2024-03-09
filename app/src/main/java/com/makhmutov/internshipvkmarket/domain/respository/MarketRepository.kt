package com.makhmutov.internshipvkmarket.domain.respository

import com.makhmutov.internshipvkmarket.domain.entities.RequestMarketItemListResult
import com.makhmutov.internshipvkmarket.domain.entities.RequestOneMarketItemResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MarketRepository {

    val marketItemsFlow: StateFlow<RequestMarketItemListResult>
    fun getOneMarketItemByIdFlow(id: Int): Flow<RequestOneMarketItemResult>
    suspend fun requestMarketItems()

}