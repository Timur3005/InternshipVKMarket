package com.makhmutov.internshipvkmarket.presentation.screens.oneproduct

import androidx.lifecycle.ViewModel
import com.makhmutov.internshipvkmarket.domain.entities.RequestOneMarketItemResult
import com.makhmutov.internshipvkmarket.domain.usecases.GetOneMarketItemByIdUseCase
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class OneProductViewModel @Inject constructor(
    productId: Int,
    getOneMarketItemByIdUseCase: GetOneMarketItemByIdUseCase,
): ViewModel() {

    val productFlow = getOneMarketItemByIdUseCase(productId)
        .map {
            when (it) {
                is RequestOneMarketItemResult.Error ->
                    OneProductScreenState.Error
                is RequestOneMarketItemResult.Success ->
                    OneProductScreenState.Product(it.marketItem)
            }
        }
        .onStart {
            emit(OneProductScreenState.Loading)
        }

}