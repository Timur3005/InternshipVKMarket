package com.makhmutov.internshipvkmarket.domain.respository

import com.makhmutov.internshipvkmarket.domain.entities.RequestMarketItemResult
import kotlinx.coroutines.flow.StateFlow

interface MarketRepository {

    val marketItemsFlow: StateFlow<RequestMarketItemResult>
    suspend fun requestMarketItems()

}