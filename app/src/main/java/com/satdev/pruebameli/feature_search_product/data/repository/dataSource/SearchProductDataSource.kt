package com.satdev.pruebameli.feature_search_product.data.repository.dataSource

import com.satdev.pruebameli.feature_search_product.data.model.SearchProductResult
import okhttp3.Response

interface SearchProductDataSource {
    suspend fun searchProduct(query:String) : SearchProductResult
}