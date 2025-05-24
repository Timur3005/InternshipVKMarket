package com.makhmutov.internshipvkmarket.presentation.naviagtion

import com.makhmutov.internshipvkmarket.domain.entities.MarketItemEntity

sealed class ScreenNavigation(
    val route: String
) {
    data object Products : ScreenNavigation(PRODUCTS_ROUTE)
    data object Profile : ScreenNavigation(PROFILE)
    data object OneProduct : ScreenNavigation(COMMENTS_ROUTE) {
        fun getRouteWithArgs(product: MarketItemEntity): String {
            return "$ONE_PRODUCT_ROOT/${product.id}"
        }
    }

    data object Login : ScreenNavigation(LOGIN)
    data object Authorization : ScreenNavigation(AUTHORIZATION)
    data object Registration : ScreenNavigation(REGISTRATION)
    data object Greeting : ScreenNavigation(GREETING)
    data object Basket : ScreenNavigation(BASKET)

    companion object {
        const val KEY_ONE_PRODUCT_ID = "market_item"
        private const val PRODUCTS_ROUTE = "products"
        private const val PROFILE = "profile"
        private const val LOGIN = "login"
        private const val BASKET = "basket"
        private const val AUTHORIZATION = "authorization"
        private const val GREETING = "greeting"
        private const val REGISTRATION = "registration"
        private const val ONE_PRODUCT_ROOT = "one_product"
        private const val COMMENTS_ROUTE = "$ONE_PRODUCT_ROOT/{$KEY_ONE_PRODUCT_ID}"
    }
}
