package com.makhmutov.internshipvkmarket.presentation.naviagtion

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.makhmutov.internshipvkmarket.presentation.naviagtion.ScreenNavigation.Companion.KEY_ONE_PRODUCT_ID

@Composable
fun AppNavigationGraph(
    navController: NavHostController,
    productsScreen: @Composable () -> Unit,
    oneProductScreen: @Composable (Int) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = ScreenNavigation.Products.route
    ) {
        composable(ScreenNavigation.Products.route) {
            productsScreen()
        }
        composable(ScreenNavigation.OneProduct.route) {
            val productId = it.arguments?.getInt(KEY_ONE_PRODUCT_ID)
                ?: throw RuntimeException("id isn't exist")
            oneProductScreen(productId)
        }
    }
}