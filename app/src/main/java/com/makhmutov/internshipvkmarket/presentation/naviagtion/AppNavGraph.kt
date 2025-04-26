package com.makhmutov.internshipvkmarket.presentation.naviagtion

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.makhmutov.internshipvkmarket.presentation.naviagtion.ScreenNavigation.Companion.KEY_ONE_PRODUCT_ID

@Composable
fun AppNavigationGraph(
    navController: NavHostController,
    productsScreen: @Composable () -> Unit,
    oneProductScreen: @Composable () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = ScreenNavigation.Products.route
    ) {
        composable(ScreenNavigation.Products.route) {
            productsScreen()
        }
        composable(
            route = ScreenNavigation.OneProduct.route,
            arguments = listOf(
                navArgument(KEY_ONE_PRODUCT_ID){
                    type = NavType.IntType
                }
            )
        ) {
            oneProductScreen()
        }
    }
}