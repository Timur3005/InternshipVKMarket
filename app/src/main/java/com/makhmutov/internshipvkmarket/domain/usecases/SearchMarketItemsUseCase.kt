package com.makhmutov.internshipvkmarket.domain.usecases

import com.makhmutov.internshipvkmarket.domain.respository.MarketRepository
import javax.inject.Inject

class SearchMarketItemsUseCase @Inject constructor(
    private val repository: MarketRepository,
) {
    suspend operator fun invoke(request: String) = repository.searchMarketItems(request)
}