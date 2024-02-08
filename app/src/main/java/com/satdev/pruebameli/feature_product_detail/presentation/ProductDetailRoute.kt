package com.satdev.pruebameli.feature_product_detail.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

@Composable
fun ProductDetailRoute(viewModel: ProductDetailViewModel, onBackClicked : () -> Unit) {
    Column {
        ProductDetailScreen(uiState = viewModel.uiState, onBackClicked = onBackClicked)
    }
}