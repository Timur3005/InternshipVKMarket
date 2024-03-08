package com.makhmutov.internshipvkmarket.data.repository

import com.makhmutov.internshipvkmarket.data.mapper.MarketMapper
import com.makhmutov.internshipvkmarket.data.network.api.ProductsApiService
import com.makhmutov.internshipvkmarket.domain.entities.MarketItemEntity
import com.makhmutov.internshipvkmarket.domain.entities.RequestMarketItemResult
import com.makhmutov.internshipvkmarket.domain.respository.MarketRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MarketRepositoryImpl @Inject constructor(
    private val apiService: ProductsApiService,
    private val mapper: MarketMapper
) : MarketRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val requestMarketItemsFlow = MutableSharedFlow<Unit>(replay = 1)

    private var firstMarketItemsIsLoaded = false

    private var lastMarketItems: List<MarketItemEntity> = listOf()

    override val marketItemsFlow: StateFlow<RequestMarketItemResult> = flow {
        requestMarketItems()
        requestMarketItemsFlow.collect {
            val resultOfRequestMarketItems = withContext(Dispatchers.IO) {
                apiService.getMarketItems()
            }
            val marketItemsEntity =
                mapper.mapResultMarketItemsContainerToMarketItemEntity(resultOfRequestMarketItems)
            lastMarketItems = marketItemsEntity
            emit(
                RequestMarketItemResult.Success(marketItemsEntity) as RequestMarketItemResult
            )
            firstMarketItemsIsLoaded = true
        }
    }
        .retry(retries = 3L) {
            delay(DELAY_BEFORE_RETRY)
            true
        }
        .catch { emit(RequestMarketItemResult.Error(it, firstMarketItemsIsLoaded, lastMarketItems)) }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = RequestMarketItemResult.Initial
        )


    override suspend fun requestMarketItems() {
        requestMarketItemsFlow.emit(Unit)
    }

    private companion object {
        const val DELAY_BEFORE_RETRY = 2000L
    }

}