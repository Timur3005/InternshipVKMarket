package com.makhmutov.internshipvkmarket.presentation.screens.products

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.makhmutov.internshipvkmarket.R
import com.makhmutov.internshipvkmarket.domain.entities.MarketItemEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    onProductClickListener: (MarketItemEntity) -> Unit
) {
    val viewModel: ProductsViewModel = hiltViewModel()

    val screenState = viewModel.productsFlow.collectAsState(initial = ProductsScreenState.Initial)
    val categories = viewModel.categories.collectAsState(initial = listOf())
    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    TopAppBarTitle(
                        categories = categories,
                        onDropdownMenuItemClickListener = {
                            scope.launch {
                                viewModel.loadNextProducts(it)
                                delay(500)
                                lazyListState.animateScrollToItem(index = 0)
                            }
                        },
                        onSearch = {
                            scope.launch {
                                viewModel.searchMarketItems(it)
                                delay(500)
                                lazyListState.animateScrollToItem(index = 0)
                            }
                        }
                    )
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) { paddingValues ->
        Products(
            paddingValues = paddingValues,
            state = screenState,
            isNotDownLoadingListener = {
                scope.launch {
                    viewModel.loadNextProducts()
                }
            },
            onProductClickListener = onProductClickListener,
            lazyListState = lazyListState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBarTitle(
    categories: State<List<String>>,
    onDropdownMenuItemClickListener: (String) -> Unit,
    onSearch: (String) -> Unit
) {
    val dropdownMenuExpanded = rememberSaveable {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        val queryState = rememberSaveable {
            mutableStateOf("")
        }
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .weight(1f)
            ,
            horizontalArrangement = Arrangement.Start
        ) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                ,
                query = queryState.value,
                onQueryChange = {
                    queryState.value = it
                },
                onSearch = {
                    onSearch(it)
                },
                active = false,
                onActiveChange = {},
                shape = RoundedCornerShape(4.dp),
                tonalElevation = 2.dp,
                placeholder = {
                    Text(
                        text = "Search product...",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            ) {

            }
        }
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.End
        ) {
            Box {
                IconButton(
                    onClick = {
                        dropdownMenuExpanded.value = !dropdownMenuExpanded.value
                    }
                ) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.baseline_filter_list_alt_24
                        ),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                DropDownFilter(
                    state = dropdownMenuExpanded,
                    list = categories.value,
                    onItemClickListener = {
                        onDropdownMenuItemClickListener(it)
                    }
                )
            }
        }
    }
}

@Composable
private fun Products(
    paddingValues: PaddingValues,
    state: State<ProductsScreenState>,
    isNotDownLoadingListener: () -> Unit,
    onProductClickListener: (MarketItemEntity) -> Unit,
    lazyListState: LazyListState,
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
            SuccessfullyProducts(
                paddingValues,
                lazyListState,
                realState,
                onProductClickListener,
                isNotDownLoadingListener
            )
        }
    }

}

@Composable
private fun SuccessfullyProducts(
    paddingValues: PaddingValues,
    lazyListState: LazyListState,
    realState: ProductsScreenState.Products,
    onProductClickListener: (MarketItemEntity) -> Unit,
    isNotDownLoadingListener: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxSize()
        ,
        contentPadding = PaddingValues(
            top = 20.dp,
            bottom = 20.dp,
            start = 8.dp,
            end = 8.dp
        ),
        state = lazyListState
    ) {
        items(items = realState.products, key = { it.id }) {
            ProductCard(
                modifier = Modifier
                    .padding(bottom = 16.dp)
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

@Composable
private fun DropDownFilter(
    state: MutableState<Boolean>,
    list: List<String>,
    onItemClickListener: (String) -> Unit,
) {
    var selectedItem by rememberSaveable {
        mutableStateOf("all")
    }
    DropdownMenu(
        expanded = state.value,
        onDismissRequest = { state.value = false }
    ) {
        list.forEach { item ->
            DropdownMenuItem(
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = item,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )
                        if (item == selectedItem) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = "Selected",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                },
                onClick = {
                    if (selectedItem != item) {
                        onItemClickListener(item)
                        selectedItem = item
                    }
                }
            )
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
            Text(text = product.title, style = MaterialTheme.typography.titleLarge
                .copy(fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(8.dp))
            SubcomposeAsyncImage(
                model = product.thumbnail,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                loading = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                },
                error = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ){
                Text(
                    text = "Cost ${product.price}$",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                if (product.discountPercentage > 0.0){
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "SALE -${product.discountPercentage}%",
                        style = MaterialTheme.typography.bodyLarge
                            .copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = product.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}