package com.makhmutov.internshipvkmarket.domain.usecases

import com.makhmutov.internshipvkmarket.domain.respository.MarketRepository
import javax.inject.Inject

/**
 * Класс-резултат запроса товара по категории
 */
class RequestByCategoryMarketItemsUseCase @Inject constructor(
    private val repository: MarketRepository,
) {
    suspend operator fun invoke(category: String) =
        repository.requestByCategoryMarketItems(category)
}