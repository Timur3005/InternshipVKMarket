package com.makhmutov.internshipvkmarket.data.repository

import com.makhmutov.internshipvkmarket.data.mapper.MarketMapper
import com.makhmutov.internshipvkmarket.data.network.api.ProductsApiService
import com.makhmutov.internshipvkmarket.domain.entities.MarketItemEntity
import com.makhmutov.internshipvkmarket.domain.entities.RequestMarketItemListResult
import com.makhmutov.internshipvkmarket.domain.entities.RequestOneMarketItemResult
import com.makhmutov.internshipvkmarket.domain.respository.MarketRepository
import com.makhmutov.internshipvkmarket.extentions.mergeWith
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

    private val searchRequestFlow = MutableSharedFlow<String>(replay = 1)
    override suspend fun searchMarketItems(request: String) {
        searchRequestFlow.emit(request)
    }

    private var searchingList = listOf<MarketItemEntity>()

    private val searchMarketItemsFlow = flow {
        searchRequestFlow.collect {
            val response = withContext(Dispatchers.IO){
                mapper.mapResultMarketItemsContainerToMarketItemEntity(
                    apiService.searchMarketItems(it)
                )
            }
            searchingList = response
            emit(
                RequestMarketItemListResult.Success(
                    marketItems = searchingList.toList(),
                    isLast = true
                ) as RequestMarketItemListResult
            )
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
                    false,
                )
            )
        }



    private var lastByCategoryMarketItems: List<MarketItemEntity> = listOf()

    private val marketItemsByCategoryFlow = flow {
        requestByCategoryFlow.collect {
            val resultOfRequestMarketItems = withContext(Dispatchers.IO) {
                apiService.getMarketItemsByCategory(category = it)
            }
            val marketItemsEntity =
                mapper
                    .mapResultMarketItemsContainerToMarketItemEntity(resultOfRequestMarketItems)
            lastByCategoryMarketItems = marketItemsEntity
            emit(
                RequestMarketItemListResult.Success(
                    marketItems = lastByCategoryMarketItems,
                    isLast = true
                ) as RequestMarketItemListResult
            )
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
                    false,
                )
            )
        }


    private val requestByCategoryFlow = MutableSharedFlow<String>(replay = 1)
    override suspend fun requestByCategoryMarketItems(category: String) {
        requestByCategoryFlow.emit(category)
    }

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
        .mergeWith(marketItemsByCategoryFlow)
        .mergeWith(searchMarketItemsFlow)

    override val marketItemsFlow: StateFlow<RequestMarketItemListResult> = coldMarketItemsFlow
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = RequestMarketItemListResult.Success(listOf())
        )


    override fun getOneMarketItemByIdFlow(id: Int): Flow<RequestOneMarketItemResult> = flow {
        val marketItem =
            lastByCategoryMarketItems.find { it.id == id } ?:
            searchingList.find { it.id == id } ?:
            lastMarketItems.find { it.id == id } ?:
            throw RuntimeException("market item isn't exist")
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

    override fun getCategories(): StateFlow<List<String>> = flow {
        val categories = mutableListOf("all")
        categories.addAll(
            withContext(Dispatchers.IO) {
                apiService.getCategories()
            }
        )
        emit(categories.toList())
    }
        .retry()
        .stateIn(
            started = SharingStarted.Eagerly,
            initialValue = listOf(),
            scope = coroutineScope
        )

    private companion object {
        const val DELAY_BEFORE_RETRY = 2000L
    }

}