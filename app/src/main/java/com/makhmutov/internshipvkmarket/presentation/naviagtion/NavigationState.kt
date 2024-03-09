package com.makhmutov.internshipvkmarket.presentation.naviagtion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.makhmutov.internshipvkmarket.domain.entities.MarketItemEntity

class NavigationState(
    val navController: NavHostController,
) {
    fun navigateToOneProduct(product: MarketItemEntity) {
        navController.navigate(ScreenNavigation.OneProduct.getRouteWithArgs(product)) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

@Composable
fun rememberNavigationState(
    navController: NavHostController = rememberNavController(),
): NavigationState {
    return remember {
        NavigationState(navController)
    }
}