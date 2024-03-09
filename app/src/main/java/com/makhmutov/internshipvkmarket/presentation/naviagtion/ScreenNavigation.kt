package com.makhmutov.internshipvkmarket.presentation.naviagtion

import android.net.Uri
import com.google.gson.Gson
import com.makhmutov.internshipvkmarket.domain.entities.MarketItemEntity

sealed class ScreenNavigation(
    val route: String
) {
    data object Products : ScreenNavigation(PRODUCTS_ROUTE)
    data object OneProduct : ScreenNavigation(COMMENTS_ROUTE) {
        fun getRouteWithArgs(product: MarketItemEntity): String {
            return "$ONE_PRODUCT_ROOT/${product.id}"
        }
    }

    companion object {
        const val KEY_ONE_PRODUCT_ID = "market_item"
        private const val PRODUCTS_ROUTE = "products"
        private const val ONE_PRODUCT_ROOT = "one_product"
        private const val COMMENTS_ROUTE = "$PRODUCTS_ROUTE/{$KEY_ONE_PRODUCT_ID}"
    }
}
