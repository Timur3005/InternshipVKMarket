package com.makhmutov.internshipvkmarket.domain.usecases

import com.makhmutov.internshipvkmarket.domain.respository.MarketRepository

class GetMarketItemsUseCase constructor(
    private val repository: MarketRepository
) {
    operator fun invoke() = repository.marketItemsFlow
}