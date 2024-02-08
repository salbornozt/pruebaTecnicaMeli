package com.satdev.pruebameli.feature_search_product.presentation

sealed interface SearchProductsEvents {
    data class OnQueryChange(val query : String) : SearchProductsEvents

    object OnClickSearchProduct : SearchProductsEvents
}