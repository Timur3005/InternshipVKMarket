package com.makhmutov.internshipvkmarket.data.repository

import com.makhmutov.internshipvkmarket.data.mapper.MarketMapper
import com.makhmutov.internshipvkmarket.data.network.api.ProductsApiService
import com.makhmutov.internshipvkmarket.domain.entities.MarketItemEntity
import com.makhmutov.internshipvkmarket.domain.respository.MarketRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MarketRepositoryImpl @Inject constructor(
    private val apiService: ProductsApiService,
    private val mapper: MarketMapper
) : MarketRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val requestMarketItemsFlow = MutableSharedFlow<Unit>(replay = 1)

    override val marketItemsFlow: StateFlow<List<MarketItemEntity>> = flow {
        requestMarketItems()
        requestMarketItemsFlow.collect{
            val resultOfRequestMarketItems = withContext(Dispatchers.IO) {
                apiService.getMarketItems()
            }
            val marketItemsEntity =
                mapper.mapResultMarketItemsContainerToMarketItemEntity(resultOfRequestMarketItems)
            emit(marketItemsEntity)
        }
    }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Eagerly,
            initialValue = listOf()
        )
    override suspend fun requestMarketItems() {
        requestMarketItemsFlow.emit(Unit)
    }

}