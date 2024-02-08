package com.satdev.pruebameli.feature_search_product.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satdev.pruebameli.core.wrapper.ApiResult
import com.satdev.pruebameli.core.wrapper.ErrorWrapper
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
            val result = searchRepository.searchProduct(uiState.searchQuery)
            when(result){
                is ApiResult.Error -> {
                    handleErrorResult(result.errorWrapper)
                }
                is ApiResult.Success -> {
                    uiState = uiState.copy(loadingState = false, productList = result?.data?.results ?: listOf())
                }
            }

        }

    }

    private fun handleErrorResult(errorWrapper: ErrorWrapper?) {
        val message :String = when(errorWrapper){
            ErrorWrapper.ServiceNotAvailable -> {
                "Ocurrio un error al obtener los resultados, revisa tu conexión a internet"
            }
            is ErrorWrapper.ServiceInternalError -> {
                "ahora mismo no es posible obtener los resultados"
            }
            ErrorWrapper.UnknownError -> {
                "Ocurrio un error inesperado al obtener los resultados"
            }
            else -> {
                "Ocurrio un error inesperado al obtener los resultados"
            }
        }
        uiState = uiState.copy(loadingState = false, errorState = true, errorMessage = message)
    }

}

data class SearchProductUiState(
    val searchQuery: String = "",
    val loadingState: Boolean = false,
    val productList: List<Product> = listOf(),
    val errorState : Boolean = false,
    val errorMessage : String? = null
)