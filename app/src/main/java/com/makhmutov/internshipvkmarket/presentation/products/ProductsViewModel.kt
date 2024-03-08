package com.makhmutov.internshipvkmarket.presentation.products

import androidx.lifecycle.ViewModel
import com.makhmutov.internshipvkmarket.domain.entities.RequestMarketItemResult
import com.makhmutov.internshipvkmarket.domain.usecases.GetMarketItemsUseCase
import com.makhmutov.internshipvkmarket.domain.usecases.RequestMarketItemsUseCase
import com.makhmutov.internshipvkmarket.extentions.mergeWith
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class ProductsViewModel @Inject constructor(
    getMarketItemsUseCase: GetMarketItemsUseCase,
    private val requestMarketItemsUseCase: RequestMarketItemsUseCase
): ViewModel()  {

    private val loadingProductsState = MutableSharedFlow<ProductsScreenState>()

    val productsFlow = getMarketItemsUseCase()
        .map {
            when(it){
                is RequestMarketItemResult.Error -> {
                    if (it.firstMarketItemsIsLoaded){
                        ProductsScreenState.Products(
                            products = it.lastMarketItems,
                            isLoadingException = true
                        )
                    }
                    else{
                        ProductsScreenState.Error
                    }
                }
                RequestMarketItemResult.Initial -> {
                    ProductsScreenState.Initial
                }
                is RequestMarketItemResult.Success -> {
                    ProductsScreenState.Products(
                        products = it.marketItems,
                    )
                }
            }
        }
        .onStart { emit(ProductsScreenState.Loading) }
        .mergeWith(loadingProductsState)

    suspend fun loadNextProducts(){
        loadingProductsState.emit(ProductsScreenState.Loading)
        requestMarketItemsUseCase()
    }

}