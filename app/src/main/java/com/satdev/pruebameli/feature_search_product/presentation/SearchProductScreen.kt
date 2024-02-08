package com.satdev.pruebameli.feature_search_product.presentation

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.satdev.pruebameli.ui.theme.PruebaMeliTheme

@Composable
fun SearchProductScreen(uiState: SearchProductUiState, onSearchProduct: () -> Unit, onQuerySearch: (String) -> Unit) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        if (uiState.loadingState) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.searchQuery,
                onValueChange = onQuerySearch,
                singleLine = true,
                label = { Text("Buscar") },
                trailingIcon = {
                    IconButton(onClick = onSearchProduct) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            )
        }
        ProductList(uiState.productList) {

        }
    }
}

@Preview
@Composable
fun SearchProductScreenPreview() {
    PruebaMeliTheme {
        Surface {
            SearchProductScreen(uiState = SearchProductUiState(), onSearchProduct = {}, onQuerySearch = {})
        }
    }
}