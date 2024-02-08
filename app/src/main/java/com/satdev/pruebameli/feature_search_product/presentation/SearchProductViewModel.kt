package com.satdev.pruebameli.feature_search_product.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satdev.pruebameli.feature_search_product.data.model.Product
import com.satdev.pruebameli.feature_search_product.domain.SearchProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchProductViewModel @Inject constructor(private val searchRepository: SearchProductRepository) :
    ViewModel() {
    var uiState by mutableStateOf(
        SearchProductUiState()
    )
        private set


    fun onEvent(events: SearchProductsEvents) {
        when (events) {
            is SearchProductsEvents.OnQueryChange -> {
                uiState = uiState.copy(searchQuery = events.query)
            }

            SearchProductsEvents.OnClickSearchProduct -> {
                Log.d("sat_tag", "onEvent: buscar ${uiState.searchQuery}")
                searchProducts()
            }
        }
    }

    private fun searchProducts() {
        if (uiState.searchQuery.isEmpty()) return
        uiState = uiState.copy(loadingState = true)
        viewModelScope.launch {
            val result = try {
                searchRepository.searchProduct(uiState.searchQuery)
            } catch (e: Exception) {
                Log.e("TAG", "searchProducts: ", e)
                // TODO: handle error
                uiState = uiState.copy(loadingState = false)
                return@launch
            }
            uiState = uiState.copy(loadingState = false, productList = result?.results ?: listOf())
        }

    }

}

data class SearchProductUiState(
    val searchQuery: String = "",
    val loadingState: Boolean = false,
    val productList: List<Product> = listOf()
)