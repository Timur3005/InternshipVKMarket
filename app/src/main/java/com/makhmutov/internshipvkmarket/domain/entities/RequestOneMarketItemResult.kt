package com.makhmutov.internshipvkmarket.domain.entities

sealed interface RequestOneMarketItemResult {
    data class Error(
        val exception: Throwable = RuntimeException()
    ) : RequestOneMarketItemResult
    data class Success(
        val marketItem: MarketItemEntity
    ) : RequestOneMarketItemResult

}