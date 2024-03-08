package com.makhmutov.internshipvkmarket.domain.entities

sealed interface RequestMarketItemResult {
    data object Initial : RequestMarketItemResult
    data class Error(
        val exception: Throwable,
        val firstMarketItemsIsLoaded: Boolean,
        val lastMarketItems: List<MarketItemEntity> = listOf()
    ) : RequestMarketItemResult
    data class Success(
        val marketItems: List<MarketItemEntity>
    ) : RequestMarketItemResult

}