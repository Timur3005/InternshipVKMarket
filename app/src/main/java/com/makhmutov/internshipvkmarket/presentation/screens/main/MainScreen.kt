package com.makhmutov.internshipvkmarket.presentation.screens.main

import androidx.compose.runtime.Composable
import com.example.auth_impl.presentation.auth.AuthorizationScreen
import com.example.auth_impl.presentation.greeting.GreetingScreen
import com.example.auth_impl.presentation.login.LoginScreen
import com.example.auth_impl.presentation.registration.RegistrationScreen
import com.makhmutov.internshipvkmarket.presentation.naviagtion.AppNavigationGraph
import com.makhmutov.internshipvkmarket.presentation.naviagtion.rememberNavigationState
import com.makhmutov.internshipvkmarket.presentation.screens.oneproduct.OneProductScreen
import com.makhmutov.internshipvkmarket.presentation.screens.products.ProductsScreen

@Composable
internal fun MainScreen() {
    val navState = rememberNavigationState()
    AppNavigationGraph(
        navController = navState.navController,
        productsScreen = {
            ProductsScreen{
                navState.navigateToOneProduct(it)
            }
        },
        oneProductScreen = {
            OneProductScreen()
        },
        loginScreen = {
            LoginScreen {
                navState.navigate(it)
            }
        },
        registrationScreen = {
            RegistrationScreen {
                navState.navigate(it)
            }
        },
        authScreen = {
            AuthorizationScreen {
                navState.navigate(it)
            }
        },
        greetingScreen = {
            GreetingScreen {
                navState.navigate(it)
            }
        }
    )
}