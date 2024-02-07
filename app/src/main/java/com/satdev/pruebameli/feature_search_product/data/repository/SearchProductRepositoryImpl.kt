package com.satdev.pruebameli.feature_search_product.data.repository

import android.util.Log
import com.satdev.pruebameli.feature_search_product.data.model.SearchProductResult
import com.satdev.pruebameli.feature_search_product.data.repository.dataSource.SearchProductDataSource
import com.satdev.pruebameli.feature_search_product.domain.SearchProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class SearchProductRepositoryImpl @Inject constructor(private val searchProductDataSource: SearchProductDataSource) :SearchProductRepository {
    override suspend fun searchProduct(query:String): SearchProductResult? {
        return withContext(Dispatchers.IO) {
            searchProductDataSource.searchProduct(query)
        }
    }
}