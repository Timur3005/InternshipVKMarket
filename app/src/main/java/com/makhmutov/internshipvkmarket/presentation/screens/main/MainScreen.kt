package com.makhmutov.internshipvkmarket.presentation.screens.main

import androidx.compose.runtime.Composable
import com.makhmutov.internshipvkmarket.presentation.naviagtion.AppNavigationGraph
import com.makhmutov.internshipvkmarket.presentation.naviagtion.rememberNavigationState
import com.makhmutov.internshipvkmarket.presentation.screens.oneproduct.OneProductScreen
import com.makhmutov.internshipvkmarket.presentation.screens.products.ProductsScreen

@Composable
fun MainScreen() {
    val navState = rememberNavigationState()
    AppNavigationGraph(
        navController = navState.navController,
        productsScreen = {
            ProductsScreen{
                navState.navigateToOneProduct(it)
            }
        },
        oneProductScreen = {
            OneProductScreen(productId = it)
        }
    )
}