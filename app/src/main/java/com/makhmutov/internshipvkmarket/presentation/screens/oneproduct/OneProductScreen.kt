package com.makhmutov.internshipvkmarket.presentation.screens.oneproduct

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.makhmutov.internshipvkmarket.R
import com.makhmutov.internshipvkmarket.presentation.app.getApplicationComponent

@Composable
fun OneProductScreen(
    productId: Int
) {
    val component = getApplicationComponent()
        .getOneProductComponentFactory()
        .create(productId = productId)

    val viewModelFactory = component.getViewModelFactory()

    val viewModel: OneProductViewModel = viewModel(
        factory = viewModelFactory
    )
    val screenState = viewModel.productFlow.collectAsState(initial = OneProductScreenState.Initial)

    OneProduct(screenState)
}

@Composable
private fun OneProduct(state: State<OneProductScreenState>) {

    when (val realState = state.value) {

        OneProductScreenState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(R.string.error),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        OneProductScreenState.Initial -> {}

        OneProductScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }

        is OneProductScreenState.Product -> {
            Product(realState)
        }
    }
}

@Composable
private fun Product(productState: OneProductScreenState.Product) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .scrollable(
                state = rememberScrollableState{ 0.4f },
                orientation = Orientation.Vertical
            )
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = productState.product.title, style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(8.dp))
        AsyncImage(
            model = productState.product.thumbnail,
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = productState.product.description)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = productState.product.brand)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = productState.product.category)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = productState.product.discountPercentage.toString())
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = productState.product.price.toString())
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = productState.product.stock.toString())
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = productState.product.rating.toString())
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = productState.product.images.size.toString())
    }
}