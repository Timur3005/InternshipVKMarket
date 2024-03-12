package com.makhmutov.internshipvkmarket.presentation.screens.oneproduct

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import coil.compose.SubcomposeAsyncImage
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Product(productState: OneProductScreenState.Product) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp, top = 20.dp)
            .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = productState.product.title, style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(12.dp))

        HorizontalPager(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .clip(RoundedCornerShape(4.dp))
                .fillMaxWidth(),
            state = rememberPagerState(
                pageCount = { productState.product.images.size }
            ),
            pageSpacing = 10.dp
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                SubcomposeAsyncImage(
                    model = productState.product.images[it],
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .height(200.dp),
                    loading = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        }
                    },
                    error = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ProductCharacteristic(
                modifier = Modifier
                    .padding(end = 4.dp)
                    .fillMaxWidth()
                    .weight(0.30f)
                ,
                characteristicName = stringResource(R.string.price),
                characteristic = "${productState.product.price}$"
            )
            ProductCharacteristic(
                modifier = Modifier
                    .padding(start = 4.dp, end = 4.dp)
                    .fillMaxWidth()
                    .weight(0.4f)
                ,
                characteristicName = stringResource(R.string.discount),
                characteristic = "${productState.product.discountPercentage}%"
            )
            ProductCharacteristic(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .fillMaxWidth()
                    .weight(0.4f)
                ,
                characteristicName = stringResource(R.string.rating),
                characteristic = stringResource(R.string.out_of_5, productState.product.rating)
            )
        }

        ProductCharacteristic(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            characteristicName = stringResource(R.string.description),
            characteristic = productState.product.description
        )

        ProductCharacteristic(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            characteristicName = stringResource(R.string.brand),
            characteristic = productState.product.brand
        )

        ProductCharacteristic(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            characteristicName = stringResource(R.string.category),
            characteristic = productState.product.category
        )

        ProductCharacteristic(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            characteristicName = stringResource(R.string.stock),
            characteristic = stringResource(R.string.products_count, productState.product.stock)
        )


    }
}

@Composable
private fun ProductCharacteristic(
    modifier: Modifier = Modifier,
    characteristicName: String,
    characteristic: String,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Text(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp),
            text = characteristicName,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
            text = characteristic,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}