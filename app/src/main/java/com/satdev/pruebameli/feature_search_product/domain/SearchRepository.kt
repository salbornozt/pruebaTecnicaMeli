package com.satdev.pruebameli.feature_search_product.domain

import com.satdev.pruebameli.feature_search_product.data.model.SearchProductResult

interface SearchRepository {
    suspend fun searchProduct(query:String) : SearchProductResult
}