package com.makhmutov.internshipvkmarket.presentation.screens.oneproduct

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makhmutov.internshipvkmarket.data.local.MarketItemDao
import com.makhmutov.internshipvkmarket.domain.entities.MarketItemEntity
import com.makhmutov.internshipvkmarket.domain.entities.RequestOneMarketItemResult
import com.makhmutov.internshipvkmarket.domain.usecases.GetOneMarketItemByIdUseCase
import com.makhmutov.internshipvkmarket.presentation.naviagtion.ScreenNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OneProductViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getOneMarketItemByIdUseCase: GetOneMarketItemByIdUseCase,
    private val marketItemDao: MarketItemDao,
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

    fun saveProduct(marketItemEntity: MarketItemEntity) {
        viewModelScope.launch {
            marketItemDao.insert(marketItemEntity)
        }
    }

}