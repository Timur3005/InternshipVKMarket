package com.makhmutov.internshipvkmarket.presentation.screens.main

import androidx.compose.runtime.Composable
import com.example.auth_impl.presentation.auth.AuthorizationScreen
import com.example.auth_impl.presentation.greeting.GreetingScreen
import com.example.auth_impl.presentation.login.LoginScreen
import com.example.auth_impl.presentation.profile.ProfileScreen
import com.example.auth_impl.presentation.registration.RegistrationScreen
import com.makhmutov.internshipvkmarket.presentation.naviagtion.AppNavigationGraph
import com.makhmutov.internshipvkmarket.presentation.naviagtion.ScreenNavigation
import com.makhmutov.internshipvkmarket.presentation.naviagtion.rememberNavigationState
import com.makhmutov.internshipvkmarket.presentation.screens.oneproduct.OneProductScreen
import com.makhmutov.internshipvkmarket.presentation.screens.products.ProductsScreen

@Composable
internal fun MainScreen() {
    val navState = rememberNavigationState()
    AppNavigationGraph(
        navController = navState.navController,
        productsScreen = {
            ProductsScreen(
                onProductClickListener = { navState.navigateToOneProduct(it) },
                onProfileClick = {
                    navState.navigate(ScreenNavigation.Profile.route)
                }
            )

        },
        oneProductScreen = {
            OneProductScreen()
        },
        loginScreen = {
            LoginScreen {
                navState.navigateWithReplaceCurrent(it)
            }
        },
        registrationScreen = {
            RegistrationScreen {
                navState.navigateWithReplaceCurrent(it)
            }
        },
        authScreen = {
            AuthorizationScreen {
                navState.navigate(it)
            }
        },
        greetingScreen = {
            GreetingScreen {
                navState.navigateWithReplaceCurrent(it)
            }
        },
        profileScreen = {
            ProfileScreen {
                navState.navigateWithReplaceCurrent(it)
            }
        }
    )
}