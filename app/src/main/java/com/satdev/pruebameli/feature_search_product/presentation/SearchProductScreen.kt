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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.satdev.pruebameli.ui.theme.PruebaMeliTheme
import kotlinx.coroutines.launch

@Composable
fun SearchProductScreen(
        uiState: SearchProductUiState,
        onSearchProduct: () -> Unit,
        onQuerySearch: (String) -> Unit,
        onProductClick: (String?) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    if (uiState.errorState) {
        LaunchedEffect(uiState.errorMessage != null) {
            snackbarHostState.showSnackbar(uiState.errorMessage.toString())
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) {
        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (uiState.loadingState) {
                LinearProgressIndicator(
                        modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp),
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                )
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
                        }, keyboardActions = KeyboardActions {
                    onSearchProduct()
                }
                )
            }
            ProductList(uiState.productList, onProductClick = onProductClick)
        }
    }

}

@Preview
@Composable
fun SearchProductScreenPreview() {
    PruebaMeliTheme {
        Surface {
            SearchProductScreen(
                    uiState = SearchProductUiState(),
                    onSearchProduct = {},
                    onQuerySearch = {}, onProductClick = {})
        }
    }
}