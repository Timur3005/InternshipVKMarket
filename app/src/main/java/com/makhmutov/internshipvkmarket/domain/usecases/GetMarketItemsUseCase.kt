package com.makhmutov.internshipvkmarket.domain.usecases

import com.makhmutov.internshipvkmarket.domain.respository.MarketRepository
import javax.inject.Inject

/**
 * Класс-резултат запроса товара
 */
class GetMarketItemsUseCase @Inject constructor(
    private val repository: MarketRepository
) {
    operator fun invoke() = repository.marketItemsFlow
}