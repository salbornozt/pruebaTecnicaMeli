package com.satdev.pruebameli.feature_search_product.data.repository

import com.satdev.pruebameli.feature_search_product.data.model.SearchProductResult
import com.satdev.pruebameli.feature_search_product.data.repository.dataSource.SearchProductDataSource
import com.satdev.pruebameli.feature_search_product.domain.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val searchProductDataSource: SearchProductDataSource) :SearchRepository {
    override suspend fun searchProduct(query:String): SearchProductResult {
        return searchProductDataSource.searchProduct(query)
    }
}