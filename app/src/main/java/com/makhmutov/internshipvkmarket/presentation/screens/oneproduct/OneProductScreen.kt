package com.makhmutov.internshipvkmarket.presentation.screens.oneproduct

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.makhmutov.internshipvkmarket.presentation.app.getApplicationComponent
import com.makhmutov.internshipvkmarket.presentation.screens.products.ProductsScreenState

@Composable
fun OneProductScreen(
    productId: Int
) {
    val component = getApplicationComponent().getOneProductComponentFactory().create(productId)
    val viewModelFactory = component.getViewModelFactory()
    val viewModel: OneProductViewModel = viewModel(
        factory = viewModelFactory
    )
    val screenState = viewModel.productFlow.collectAsState(initial = OneProductScreenState.Initial)

    OneProduct(screenState)
}

@Composable
fun OneProduct(state: State<OneProductScreenState>) {

    when(val realState = state.value){

        OneProductScreenState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error")
            }
        }
        OneProductScreenState.Initial -> {}

        OneProductScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is OneProductScreenState.Product -> {
            Product(realState)
        }
    }
}

@Composable
private fun Product(realState: OneProductScreenState.Product) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = realState.product.thumbnail,
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .fillMaxWidth()
        )
    }
}