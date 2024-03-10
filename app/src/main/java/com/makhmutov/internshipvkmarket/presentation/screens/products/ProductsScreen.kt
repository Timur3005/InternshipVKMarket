package com.makhmutov.internshipvkmarket.presentation.screens.products

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.makhmutov.internshipvkmarket.R
import com.makhmutov.internshipvkmarket.domain.entities.MarketItemEntity
import com.makhmutov.internshipvkmarket.presentation.app.getApplicationComponent

@Composable
fun ProductsScreen(
    onProductClickListener: (MarketItemEntity) -> Unit
) {

    val component = getApplicationComponent()
    val viewModel: ProductsViewModel = viewModel(
        factory = component.getViewModelFactory()
    )
    val screenState = viewModel.productsFlow.collectAsState(initial = ProductsScreenState.Initial)

    Products(
        state = screenState,
        isNotDownLoadingListener = { viewModel.loadNextProducts() },
        onProductClickListener = onProductClickListener
    )

}

@Composable
private fun Products(
    state: State<ProductsScreenState>,
    isNotDownLoadingListener: () -> Unit,
    onProductClickListener: (MarketItemEntity) -> Unit,
) {

    when (val realState = state.value) {

        ProductsScreenState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.error),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        ProductsScreenState.Initial -> {}

        ProductsScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }

        is ProductsScreenState.Products -> {
            val lazyListState = rememberLazyListState()
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    top = 16.dp,
                    bottom = 16.dp,
                    start = 8.dp,
                    end = 8.dp
                ),
                state = lazyListState
            ) {
                item {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(R.string.products),
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
                items(items = realState.products, key = { it.id }) {
                    ProductCard(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        product = it,
                        onProductClickListener = {
                            onProductClickListener(it)
                        }
                    )
                }
                item {
                    LastElement(realState, isNotDownLoadingListener)
                }
            }
        }
    }

}

@Composable
private fun LastElement(
    realState: ProductsScreenState.Products,
    isNotDownLoadingListener: () -> Unit,
) {
    when (realState.stateInLast) {

        ProductsStateInLast.NOTHING -> {
            if (!realState.isLast) {
                SideEffect {
                    isNotDownLoadingListener()
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.no_more_products),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }

        ProductsStateInLast.LOADING -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }

        ProductsStateInLast.ERROR -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.error),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductCard(
    modifier: Modifier = Modifier,
    product: MarketItemEntity,
    onProductClickListener: () -> Unit,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        onClick = {
            onProductClickListener()
        }
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = product.title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            AsyncImage(
                model = product.thumbnail,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = product.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}