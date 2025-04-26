package com.makhmutov.internshipvkmarket.domain.usecases

import com.makhmutov.internshipvkmarket.domain.respository.MarketRepository
import javax.inject.Inject

/**
 * Класс-резултат запроса товара по категории
 */
class RequestMarketItemsUseCase @Inject constructor(
    private val repository: MarketRepository
) {
    suspend operator fun invoke() = repository.requestMarketItems()
}