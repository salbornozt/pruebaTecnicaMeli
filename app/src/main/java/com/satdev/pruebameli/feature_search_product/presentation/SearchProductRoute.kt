package com.satdev.pruebameli.feature_search_product.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

@Composable
fun SearchProductRoute(viewModel: SearchProductViewModel, onShowProductDetail : () -> Unit) {
    Column {
        SearchProductScreen(uiState = viewModel.uiState, onQuerySearch = {
            viewModel.onEvent(SearchProductsEvents.OnQueryChange(it))
        }, onSearchProduct = {
            viewModel.onEvent(SearchProductsEvents.OnClickSearchProduct)
        })
    }
}