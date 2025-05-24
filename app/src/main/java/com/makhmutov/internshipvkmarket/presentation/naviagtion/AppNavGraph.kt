package com.makhmutov.internshipvkmarket.presentation.naviagtion

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.makhmutov.internshipvkmarket.presentation.naviagtion.ScreenNavigation.Companion.KEY_ONE_PRODUCT_ID

@Composable
internal fun AppNavigationGraph(
    navController: NavHostController,
    productsScreen: @Composable () -> Unit,
    oneProductScreen: @Composable () -> Unit,
    loginScreen: @Composable () -> Unit,
    registrationScreen: @Composable () -> Unit,
    authScreen: @Composable () -> Unit,
    greetingScreen: @Composable () -> Unit,
    profileScreen: @Composable () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = ScreenNavigation.Greeting.route
    ) {
        composable(ScreenNavigation.Login.route) {
            loginScreen()
        }
        composable(ScreenNavigation.Registration.route) {
            registrationScreen()
        }
        composable(ScreenNavigation.Authorization.route) {
            authScreen()
        }
        composable(ScreenNavigation.Greeting.route) {
            greetingScreen()
        }
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

        composable(ScreenNavigation.Profile.route) {
            profileScreen()
        }
    }
}