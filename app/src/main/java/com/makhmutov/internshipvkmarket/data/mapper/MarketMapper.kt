package com.makhmutov.internshipvkmarket.data.mapper

import com.makhmutov.internshipvkmarket.data.network.models.MarketItemDto
import com.makhmutov.internshipvkmarket.data.network.models.RequestMarketItemsContainer
import com.makhmutov.internshipvkmarket.domain.entities.MarketItemEntity
import javax.inject.Inject

class MarketMapper @Inject constructor() {

    fun mapResultMarketItemsContainerToMarketItemEntity(
        result: RequestMarketItemsContainer
    ): List<MarketItemEntity> {
        return result.products.map {
            mapMarketItemDtoToEntity(it)
        }
    }

    private fun mapMarketItemDtoToEntity(item: MarketItemDto): MarketItemEntity{
        return MarketItemEntity(
            id = item.id,
            brand = item.brand,
            category = item.category,
            description = item.description,
            discountPercentage = item.discountPercentage,
            images = item.images,
            price = item.price,
            rating = item.rating,
            stock = item.stock,
            thumbnail = item.thumbnail,
            title = item.title
        )
    }
}