package com.makhmutov.internshipvkmarket.domain.entities

sealed interface RequestMarketItemListResult {
    data class Error(
        val exception: Throwable,
        val firstMarketItemsIsLoaded: Boolean,
        val lastMarketItems: List<MarketItemEntity> = listOf()
    ) : RequestMarketItemListResult
    data class Success(
        val marketItems: List<MarketItemEntity>,
        val isLast: Boolean = false
    ) : RequestMarketItemListResult

}