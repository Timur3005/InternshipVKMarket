package com.makhmutov.internshipvkmarket.data.network.models

import com.google.gson.annotations.SerializedName

data class RequestMarketItemsContainer(
    @SerializedName("products") val products: List<MarketItemDto>
)
