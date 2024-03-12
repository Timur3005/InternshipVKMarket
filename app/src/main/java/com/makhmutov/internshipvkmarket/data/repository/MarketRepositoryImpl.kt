package com.makhmutov.internshipvkmarket.data.repository

import com.makhmutov.internshipvkmarket.data.mapper.MarketMapper
import com.makhmutov.internshipvkmarket.data.network.api.ProductsApiService
import com.makhmutov.internshipvkmarket.domain.entities.MarketItemEntity
import com.makhmutov.internshipvkmarket.domain.entities.RequestMarketItemListResult
import com.makhmutov.internshipvkmarket.domain.entities.RequestOneMarketItemResult
import com.makhmutov.internshipvkmarket.domain.respository.MarketRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
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

    private val _lastMarketItems = mutableListOf<MarketItemEntity>()
    private val lastMarketItems: List<MarketItemEntity>
        get() = _lastMarketItems.toList()

    private var skipForApi = 0

    private val coldMarketItemsFlow = flow {
        requestMarketItems()
        requestMarketItemsFlow.collect {
            val resultOfRequestMarketItems = withContext(Dispatchers.IO) {
                apiService.getMarketItems(skip = skipForApi)
            }
            skipForApi += 20
            val marketItemsEntity =
                mapper.mapResultMarketItemsContainerToMarketItemEntity(resultOfRequestMarketItems)
            _lastMarketItems.addAll(marketItemsEntity)
            emit(
                RequestMarketItemListResult.Success(
                    marketItems = lastMarketItems,
                    isLast = marketItemsEntity.isEmpty()
                ) as RequestMarketItemListResult
            )
            firstMarketItemsIsLoaded = true
        }
    }
        .retry(retries = 3L) {
            delay(DELAY_BEFORE_RETRY)
            true
        }
        .catch {
            emit(
                RequestMarketItemListResult.Error(
                    it,
                    firstMarketItemsIsLoaded,
                    lastMarketItems
                )
            )
        }

    override val marketItemsFlow: StateFlow<RequestMarketItemListResult> = coldMarketItemsFlow
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = RequestMarketItemListResult.Success(listOf())
        )

    override fun getOneMarketItemByIdFlow(id: Int): Flow<RequestOneMarketItemResult> = flow {
        val marketItem = lastMarketItems[id - 1]
        emit(RequestOneMarketItemResult.Success(marketItem) as RequestOneMarketItemResult)
    }
        .retry(3L) {
            delay(DELAY_BEFORE_RETRY)
            true
        }
        .catch { emit(RequestOneMarketItemResult.Error(it)) }

    override suspend fun requestMarketItems() {
        requestMarketItemsFlow.emit(Unit)
    }

    override fun getCategories(): Flow<List<String>> = flow {
        emit(
            withContext(Dispatchers.IO) {
                apiService.getCategories()
            }
        )
    }.retry()

    private companion object {
        const val DELAY_BEFORE_RETRY = 2000L
    }

}