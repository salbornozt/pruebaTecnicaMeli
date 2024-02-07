package com.satdev.pruebameli.core.api

import com.satdev.pruebameli.SEARCH_URL
import com.satdev.pruebameli.feature_search_product.data.model.SearchProductResult
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(SEARCH_URL)
    suspend fun searchProducts(@Query("q") query : String) : SearchProductResult?
}