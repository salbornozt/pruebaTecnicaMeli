package com.satdev.pruebameli.feature_search_product.data.repository.dataSourceImpl

import com.satdev.pruebameli.core.api.ApiService
import com.satdev.pruebameli.feature_search_product.data.model.SearchProductResult
import com.satdev.pruebameli.feature_search_product.data.repository.dataSource.SearchProductDataSource
import javax.inject.Inject

class SearchProductDataSourceImpl @Inject constructor(private val apiService: ApiService) : SearchProductDataSource {
    override suspend fun searchProduct(query:String): SearchProductResult {
        return apiService.searchProducts(query)
    }
}