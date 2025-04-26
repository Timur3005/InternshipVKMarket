package com.makhmutov.internshipvkmarket.presentation.screens.oneproduct

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.makhmutov.internshipvkmarket.domain.entities.RequestOneMarketItemResult
import com.makhmutov.internshipvkmarket.domain.usecases.GetOneMarketItemByIdUseCase
import com.makhmutov.internshipvkmarket.presentation.naviagtion.ScreenNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class OneProductViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getOneMarketItemByIdUseCase: GetOneMarketItemByIdUseCase,
): ViewModel() {

    private val productId by lazy {
        checkNotNull(savedStateHandle[ScreenNavigation.KEY_ONE_PRODUCT_ID]) as Int
    }

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