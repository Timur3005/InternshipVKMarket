package com.makhmutov.internshipvkmarket.domain.entities

/**
 * Класс-резултат запроса товара
 */
sealed interface RequestOneMarketItemResult {
    data class Error(
        val exception: Throwable = RuntimeException()
    ) : RequestOneMarketItemResult
    data class Success(
        val marketItem: MarketItemEntity
    ) : RequestOneMarketItemResult
}