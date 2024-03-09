package com.makhmutov.internshipvkmarket.presentation.screens.products

import com.makhmutov.internshipvkmarket.domain.entities.MarketItemEntity

sealed interface ProductsScreenState {
    data object Initial : ProductsScreenState
    data object Loading : ProductsScreenState
    data object Error : ProductsScreenState
    data class Products(
        val products: List<MarketItemEntity>,
        val stateInLast: ProductsStateInLast = ProductsStateInLast.NOTHING,
        val isLast: Boolean = false
    ) : ProductsScreenState
}

enum class ProductsStateInLast{
    NOTHING,
    LOADING,
    ERROR
}