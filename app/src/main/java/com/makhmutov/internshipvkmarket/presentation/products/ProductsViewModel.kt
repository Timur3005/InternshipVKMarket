package com.makhmutov.internshipvkmarket.presentation.products

import androidx.lifecycle.ViewModel
import com.makhmutov.internshipvkmarket.domain.usecases.GetMarketItemsUseCase
import com.makhmutov.internshipvkmarket.domain.usecases.RequestMarketItemsUseCase
import javax.inject.Inject

class ProductsViewModel @Inject constructor(
    getMarketItemsUseCase: GetMarketItemsUseCase,
    private val requestMarketItemsUseCase: RequestMarketItemsUseCase
): ViewModel()  {

}