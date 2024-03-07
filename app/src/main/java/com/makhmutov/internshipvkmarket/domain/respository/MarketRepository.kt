package com.makhmutov.internshipvkmarket.domain.respository

import com.makhmutov.internshipvkmarket.domain.entities.MarketItemEntity
import kotlinx.coroutines.flow.StateFlow

interface MarketRepository {

    val marketItemsFlow: StateFlow<List<MarketItemEntity>>
    suspend fun requestMarketItems()

}