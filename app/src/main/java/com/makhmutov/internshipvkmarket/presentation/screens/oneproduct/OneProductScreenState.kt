package com.makhmutov.internshipvkmarket.presentation.screens.oneproduct

import com.makhmutov.internshipvkmarket.domain.entities.MarketItemEntity

sealed interface OneProductScreenState {
    data object Initial : OneProductScreenState
    data object Loading : OneProductScreenState
    data object Error : OneProductScreenState
    data class Product(
        val product: MarketItemEntity,
    ) : OneProductScreenState
}