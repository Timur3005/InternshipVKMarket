package com.makhmutov.internshipvkmarket.presentation.screens.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makhmutov.internshipvkmarket.domain.entities.RequestMarketItemListResult
import com.makhmutov.internshipvkmarket.domain.usecases.GetCategoriesUseCase
import com.makhmutov.internshipvkmarket.domain.usecases.GetMarketItemsUseCase
import com.makhmutov.internshipvkmarket.domain.usecases.RequestByCategoryMarketItemsUseCase
import com.makhmutov.internshipvkmarket.domain.usecases.RequestMarketItemsUseCase
import com.makhmutov.internshipvkmarket.extentions.mergeWith
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductsViewModel @Inject constructor(
    getMarketItemsUseCase: GetMarketItemsUseCase,
    getCategoriesUseCase: GetCategoriesUseCase,
    private val requestMarketItemsUseCase: RequestMarketItemsUseCase,
    private val requestByCategoryMarketItemsUseCase: RequestByCategoryMarketItemsUseCase,
) : ViewModel() {

    private val loadingProductsState = MutableSharedFlow<ProductsScreenState>()
    private val loadingCategory = MutableSharedFlow<ProductsScreenState>()

    private val marketItemsFlow = getMarketItemsUseCase()
    val productsFlow = marketItemsFlow
        .filter {
            !(it is RequestMarketItemListResult.Success && it.marketItems.isEmpty())
        }
        .map {
            when (it) {
                is RequestMarketItemListResult.Error -> {
                    if (it.firstMarketItemsIsLoaded) {
                        ProductsScreenState.Products(
                            products = it.lastMarketItems,
                            stateInLast = ProductsStateInLast.ERROR
                        )
                    } else {
                        ProductsScreenState.Error
                    }
                }

                is RequestMarketItemListResult.Success -> {
                    ProductsScreenState.Products(
                        products = it.marketItems,
                        isLast = it.isLast
                    )
                }
            }
        }
        .mergeWith(loadingProductsState)
        .mergeWith(loadingCategory)
        .onStart { emit(ProductsScreenState.Loading) }

    fun loadNextProducts(category: String = EMPTY_CATEGORY) {
        viewModelScope.launch {
            if (category.isEmpty()) {
                val marketItems =
                    (marketItemsFlow.value as RequestMarketItemListResult.Success).marketItems
                loadingProductsState.emit(
                    ProductsScreenState.Products(
                        products = marketItems,
                        stateInLast = ProductsStateInLast.LOADING
                    )
                )
                requestMarketItemsUseCase()
            } else {
                loadingCategory.emit(ProductsScreenState.Loading)
                requestByCategoryMarketItemsUseCase(category)
            }
        }
    }

    val categories = getCategoriesUseCase()

    companion object {
        private const val EMPTY_CATEGORY = ""
    }
}