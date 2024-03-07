package com.makhmutov.internshipvkmarket.domain.usecases

import com.makhmutov.internshipvkmarket.domain.respository.MarketRepository

class RequestMarketItemsUseCase constructor(
    private val repository: MarketRepository
) {
    suspend operator fun invoke() = repository.requestMarketItems()
}