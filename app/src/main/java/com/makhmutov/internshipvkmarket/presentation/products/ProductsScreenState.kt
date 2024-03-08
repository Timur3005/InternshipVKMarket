package com.makhmutov.internshipvkmarket.presentation.products

import com.makhmutov.internshipvkmarket.domain.entities.MarketItemEntity

sealed interface ProductsScreenState {
    data object Initial : ProductsScreenState
    data object Loading : ProductsScreenState
    data object Error : ProductsScreenState
    data class Products(
        val products: List<MarketItemEntity>,
        val isDownloading: Boolean = false,
        val isLoadingException: Boolean = false
    ) : ProductsScreenState
}