package com.makhmutov.internshipvkmarket.presentation.screens.oneproduct

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun OneProductScreen(
    productId: Int
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = "https://cdn.dummyjson.com/product-images/1/thumbnail.jpg",
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .fillMaxWidth()
        )
    }
}