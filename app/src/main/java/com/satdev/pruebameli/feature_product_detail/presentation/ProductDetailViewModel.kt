package com.satdev.pruebameli.feature_product_detail.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satdev.pruebameli.core.wrapper.ApiResult
import com.satdev.pruebameli.core.wrapper.ErrorWrapper
import com.satdev.pruebameli.feature_product_detail.data.model.ProductDetail
import com.satdev.pruebameli.feature_product_detail.domain.ProductDetailRepository
import com.satdev.pruebameli.feature_search_product.data.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle,
                                                 private val productDetailRepository: ProductDetailRepository) : ViewModel() {

    var uiState by mutableStateOf(
            ProductDetailUiState()
    )
        private set

    init {
        viewModelScope.launch {
            uiState =uiState.copy(loadingState = true)
            val id: String = requireNotNull(savedStateHandle["productId"])
            val result = productDetailRepository.getProductDetail(productId = id)
            when(result){
                is ApiResult.Error -> handleErrorResult(result.errorWrapper)
                is ApiResult.Success -> uiState = uiState.copy(product = result.data, loadingState = false)
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
        uiState = uiState.copy(errorState = true, errorMessage = message, loadingState = false)
    }


}

data class ProductDetailUiState(
        val loadingState: Boolean = false,
        val product: ProductDetail? = ProductDetail(),
        val errorState : Boolean = false,
        val errorMessage : String? = null
)