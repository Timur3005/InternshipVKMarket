package com.makhmutov.internshipvkmarket.presentation.screens.basket

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.makhmutov.internshipvkmarket.domain.entities.MarketItemEntity
import com.makhmutov.internshipvkmarket.presentation.screens.products.ProductCard

@Composable
internal fun BasketScreen(toItem: (MarketItemEntity) -> Unit) {

    val viewModel = hiltViewModel<BasketViewModel>()

    val state = viewModel.productsFlow.collectAsState()

    SuccessfullyProducts(
        products = state.value,
        onProductClickListener = toItem,
        onDelete = viewModel::delete
    )

}

@Composable
private fun SuccessfullyProducts(
    lazyListState: LazyListState = rememberLazyListState(),
    products: List<MarketItemEntity>,
    onProductClickListener: (MarketItemEntity) -> Unit,
    onDelete: ((MarketItemEntity) -> Unit)? = null,
) {
    LazyColumn(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxSize(),
        contentPadding = PaddingValues(
            top = 20.dp,
            bottom = 20.dp,
            start = 8.dp,
            end = 8.dp
        ),
        state = lazyListState
    ) {
        itemList(
            list = products,
            key = { it.id },
            onDelete = onDelete
        ) {

            ProductCard(
                modifier = Modifier.fillMaxWidth(),
                product = it,
                onProductClickListener = {
                    onProductClickListener(it)
                }
            )

        }
    }
}

inline fun <reified T> LazyListScope.itemList(
    list: List<T>,
    noinline key: (T) -> Any,
    hintResId: Int? = null,
    noinline onDelete: ((T) -> Unit)? = null,
    crossinline content: @Composable (T) -> Unit,
) {
    if (list.isNotEmpty()) {
        items(list, key = key) { item ->
            if (onDelete != null) {
                val threshold = with(LocalConfiguration.current) {
                    (screenWidthDp * 0.5).dp.toPx()
                }
                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = {
                        if (it == SwipeToDismissBoxValue.EndToStart) {
                            onDelete(item)
                            true
                        } else false
                    },
                    positionalThreshold = { threshold }
                )
                SwipeToDismissBox(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .animateItem(),
                    state = dismissState,
                    backgroundContent = {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.Red)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(24.dp)
                                    .align(Alignment.CenterEnd)
                                    .padding(end = 10.dp),
                                tint = Color.Unspecified
                            )
                        }
                    },
                    enableDismissFromEndToStart = true,
                    enableDismissFromStartToEnd = false,
                ) {
                    content(item)
                }
            } else {
                content(item)
            }
        }
    } else {
        hintResId?.let {
            item {
                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = stringResource(hintResId),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}

@Composable
fun Dp.toPx(): Float {
    return with(LocalDensity.current) { this@toPx.toPx() }
}