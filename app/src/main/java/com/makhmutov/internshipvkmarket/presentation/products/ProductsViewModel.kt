package com.makhmutov.internshipvkmarket.presentation.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makhmutov.internshipvkmarket.domain.entities.RequestMarketItemResult
import com.makhmutov.internshipvkmarket.domain.usecases.GetMarketItemsUseCase
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
    private val requestMarketItemsUseCase: RequestMarketItemsUseCase,
) : ViewModel() {

    private val loadingProductsState = MutableSharedFlow<ProductsScreenState>()

    private val marketItemsFlow = getMarketItemsUseCase()
    val productsFlow = marketItemsFlow
        .filter {
            !(it is RequestMarketItemResult.Success && it.marketItems.isEmpty())
        }
        .map {
            when (it) {
                is RequestMarketItemResult.Error -> {
                    if (it.firstMarketItemsIsLoaded) {
                        ProductsScreenState.Products(
                            products = it.lastMarketItems,
                            stateInLast = ProductsStateInLast.ERROR
                        )
                    } else {
                        ProductsScreenState.Error
                    }
                }

                is RequestMarketItemResult.Success -> {
                    ProductsScreenState.Products(
                        products = it.marketItems,
                        isLast = it.isLast
                    )
                }
            }
        }
        .mergeWith(loadingProductsState)
        .onStart { emit(ProductsScreenState.Loading) }

    fun loadNextProducts() {
        viewModelScope.launch {
            val marketItems =
                (marketItemsFlow.value as RequestMarketItemResult.Success).marketItems
            loadingProductsState.emit(
                ProductsScreenState.Products(
                    products = marketItems,
                    stateInLast = ProductsStateInLast.LOADING
                )
            )
            requestMarketItemsUseCase()
        }
    }

}