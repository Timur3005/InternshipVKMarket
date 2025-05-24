package com.makhmutov.internshipvkmarket.presentation.screens.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makhmutov.internshipvkmarket.data.local.MarketItemDao
import com.makhmutov.internshipvkmarket.domain.entities.MarketItemEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val marketItemDao: MarketItemDao,
) : ViewModel() {

    val productsFlow = MutableStateFlow<List<MarketItemEntity>>(listOf())

    init {
        viewModelScope.launch {
            productsFlow.value = marketItemDao.getAll()
        }
    }

    fun delete(marketItemEntity: MarketItemEntity) {
        viewModelScope.launch {
            marketItemDao.delete(marketItemEntity)
            productsFlow.value = marketItemDao.getAll()
        }
    }
}