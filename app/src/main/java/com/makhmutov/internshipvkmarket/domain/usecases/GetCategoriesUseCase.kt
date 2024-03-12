package com.makhmutov.internshipvkmarket.domain.usecases

import com.makhmutov.internshipvkmarket.domain.respository.MarketRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: MarketRepository,
) {
    operator fun invoke() = repository.getCategories()
}